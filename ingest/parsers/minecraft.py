import json
from paths import STATE_DIR, PROCESSED_DIR


LOG_PATH = "/mc-logs/latest.log"

STATE_FILE = STATE_DIR / "mc.offset"
OUTPUT_FILE = PROCESSED_DIR / "minecraft.json"


def get_offset():
    if not STATE_FILE.exists():
        return 0

    with open(STATE_FILE, "r") as f:
        return int(f.read().strip())


def save_offset(offset):
    with open(STATE_FILE, "w") as f:
        f.write(str(offset))


def parse_line(line):
    return {
        "source": "minecraft",
        "event_type": "log",
        "raw": line.strip()
    }


def process_minecraft_logs():

    if not STATE_DIR.exists():
        STATE_DIR.mkdir(parents=True, exist_ok=True)

    if not PROCESSED_DIR.exists():
        PROCESSED_DIR.mkdir(parents=True, exist_ok=True)


    if not __import__("os").path.exists(LOG_PATH):
        print(f"[MINECRAFT ERROR] Missing log: {LOG_PATH}", flush=True)
        return


    offset = get_offset()

    print(f"[MINECRAFT] Current offset: {offset}", flush=True)


    with open(LOG_PATH, "r") as f:

        f.seek(offset)

        lines = f.readlines()

        new_offset = f.tell()


    print(f"[MINECRAFT] New lines found: {len(lines)}", flush=True)


    if not lines:
        return


    events = [
        parse_line(line)
        for line in lines
    ]


    with open(OUTPUT_FILE, "a") as out:

        for event in events:
            out.write(json.dumps(event) + "\n")


    save_offset(new_offset)


    print(
        f"[INGEST] Minecraft: {len(events)} events",
        flush=True
    )
