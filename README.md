# Ubuntu Network Service Lab

A containerized service management and security monitoring lab built on Ubuntu.

This project demonstrates:

- Service orchestration
- API-driven infrastructure control
- Role-based access via Discord
- Audit logging
- Security telemetry
- Future SIEM integration with Wazuh
- Network segmentation design

---

## Project Purpose

This lab simulates a small production environment where:

- Users interact with infrastructure through a controlled interface
- Administrative actions are validated and logged
- Services are isolated and containerized
- Security events are generated and prepared for ingestion into a SIEM

Originally built as a Minecraft server controller, it has evolved into a **security-focused service orchestration platform**.

---

## High-Level Architecture

```
[ Discord User ]
        |
        v
[ Helpy Bot (Java / JDA) ]
        |
        v
[ Flask Controller API ]
        |
        v
[ Docker Engine ]
        |
        v
[ Minecraft Container ]
```

---

## Setup

```bash
git clone git@github.com:<username>/Ubuntu-network-service-lab.git
cd Ubuntu-network-service-lab
docker compose up -d --build
```

### Dockerized Deployment

Services run in containers:

- helpy-bot
- controller API
- minecraft server

#### Environment Variables

```env
DISCORD_TOKEN=
DISCORD_GUILD_ID=
API_TOKEN=
```

---

## Current Features

### Discord Bot (Helpy)

Built with:

- Java
- JDA (Java Discord API)

#### Commands

| Command | Description |
|---------|-------------|
| `/status` | Check server state |
| `/up` | Start server |
| `/down` | Stop server |
| `/restart` | Restart server |
| `/ping` | Bot health check |

---

### Role-Based Access Control

- Only users with a specific Discord role can execute admin commands
- Unauthorized users are blocked before reaching backend services

---

### Flask Controller API

Handles:

- Service state validation
- Command execution
- Cooldown enforcement
- Logging

---

### Server State Management

The system prevents invalid actions:

| State | Behavior |
|-------|----------|
| Online | Accepts restart/stop |
| Offline | Blocks stop/restart |
| Starting | Blocks new actions |
| Stopping | Blocks new actions |

---

### Cooldown Protection

To prevent abuse:

- `/up`, `/down`, `/restart` → 180 second cooldown

---

### Audit Logging (SQLite)

All actions are recorded.

#### Example Event

```
User: exampleUser
Command: restart
Result: success
Before: online
After: starting
Time: 2026-07-15T12:00:00
```

---

### Security Design (Current)

- Role-based command restriction
- API separation from bot
- Input validation
- Command cooldowns
- Internal Docker networking
- Audit logging of all actions

---

## Future Development Roadmap

### Log Ingestion Pipeline

```
Minecraft Logs
Controller Logs
        |
        v
[ Ingestion Service ]
        |
        v
[ Normalized Events ]
        |
        v
[ Wazuh Agent ]
        |
        v
[ Wazuh Manager ]
        |
        v
[ Alerts / Dashboards ]
```

### Wazuh Integration

**Goals**

- Centralized log analysis
- Threat detection
- Alert generation
- Visualization dashboards

**Example Detection Use Cases**

*Command Abuse*

- Detect: Multiple restart attempts in short time window
- Alert: High-frequency administrative activity detected

*Unauthorized Access Attempts*

- Detect: Invalid API token usage
- Alert: Unauthorized API access attempt

---

### Network Segmentation (Planned)

```
                [ Internet ]
                     |
                     v
             [ Reverse Proxy ]
                     |
        -----------------------------
        |                           |
        v                           v
 [ Bot Network ]           [ API Network ]
 (Discord Bot)             (Flask Controller)
                                   |
                                   v
                          [ Service Network ]
                          (Minecraft Container)
                                   |
                                   v
                          [ Logging Network ]
                          (Wazuh Agent)
```

**Segmentation Goals**

- Isolate services by function
- Prevent lateral movement
- Restrict container communication
- Enforce least privilege networking

**Planned Controls**

- Separate Docker networks per tier
- Firewall rules (iptables / ufw)
- API allow-listing
- Internal-only service exposure
- No direct internet access for backend services

---

### API Security Improvements

- Token-based authentication between services
- Rate limiting
- Request signing
- HTTPS with reverse proxy

---

### Hardening

- Remove Docker socket exposure
- Run containers as non-root users
- Limit container capabilities
- Add health monitoring

---

## Technologies Used

- Ubuntu Server
- Docker / Docker Compose
- Java (JDA)
- Python (Flask)
- SQLite
- Discord API
- Wazuh (planned)
