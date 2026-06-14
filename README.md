# Smart Fleet Tracking System

🇹🇷 [Türkçe](#türkçe) | 🇬🇧 [English](#english)

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![WebSocket](https://img.shields.io/badge/WebSocket-Live-brightgreen)
![Tests](https://img.shields.io/badge/Tests-19%20passing-brightgreen)
![CI/CD](https://img.shields.io/badge/CI%2FCD-GitHub%20Actions-blue)

---

## Türkçe

### Proje Açıklaması

Araç ve cihazların gerçek zamanlı takibini sağlayan backend sistemi. Java 17 ve Spring Boot 3.2 ile geliştirilmiş; WebSocket ile canlı konum akışı, GIS modülü, JWT kimlik doğrulama ve event-driven mimari içerir.

### Özellikler

- **JWT Kimlik Doğrulama** — Role bazlı yetkilendirme (ADMIN / VIEWER)
- **Cihaz Yönetimi** — Tam CRUD işlemleri
- **Gerçek Zamanlı Konum Takibi** — WebSocket ile canlı veri akışı
- **GIS Modülü:**
  - Haversine formülü ile iki nokta arası mesafe hesabı
  - Heading (yön açısı) hesaplama
  - Belirli yarıçap içindeki cihazları bulma (radius query)
- **Event-Driven Mimari** — Spring Events ile WebSocket broadcast
- **Simülasyon Sistemi** — 5 saniyede bir otomatik konum üretimi (5 araç)
- **Harita Arayüzü** — Leaflet.js ile gerçek zamanlı araç takibi
- **Global Exception Handling** — Tutarlı hata yanıtları
- **CI/CD** — GitHub Actions ile otomatik build ve test

### Teknolojiler

| Katman | Teknoloji |
|---|---|
| Dil | Java 17 |
| Framework | Spring Boot 3.2 |
| Güvenlik | Spring Security, JWT |
| ORM | Spring Data JPA / Hibernate |
| Veritabanı | PostgreSQL |
| Gerçek Zamanlı | WebSocket (STOMP) |
| Frontend | Leaflet.js |
| Test | JUnit 5, MockMvc |
| CI/CD | GitHub Actions |
| Yardımcı | Lombok, Jackson |

### Mimari

```
controller/     → REST API endpoint'leri
service/        → İş mantığı katmanı
repository/     → Veritabanı erişimi
entity/         → Veritabanı tablo sınıfları
dto/            → Request / Response nesneleri
mapper/         → Entity ↔ DTO dönüşümleri
event/          → Spring Event sınıfları
listener/       → WebSocket broadcast listener'ları
websocket/      → WebSocket konfigürasyonu
security/       → JWT filter ve konfigürasyon
simulation/     → Araç simülasyon motoru
exception/      → Global exception handling
```

### API Endpoint'leri

#### Kimlik Doğrulama
| Metod | Endpoint | Açıklama |
|---|---|---|
| POST | `/api/v1/auth/register` | Kullanıcı kaydı |
| POST | `/api/v1/auth/login` | Giriş, JWT token döner |

#### Cihaz Yönetimi
| Metod | Endpoint | Açıklama |
|---|---|---|
| POST | `/api/v1/devices` | Yeni cihaz ekle |
| GET | `/api/v1/devices` | Tüm cihazları listele |
| GET | `/api/v1/devices/{id}` | ID ile cihaz getir |
| PUT | `/api/v1/devices/{id}` | Cihaz güncelle |
| DELETE | `/api/v1/devices/{id}` | Cihaz sil |

#### Konum Takibi
| Metod | Endpoint | Açıklama |
|---|---|---|
| POST | `/api/v1/locations` | Konum kaydı ekle |
| GET | `/api/v1/locations/device/{id}` | Cihazın konum geçmişi |
| GET | `/api/v1/locations/device/{id}/latest` | Son konum |

#### Analitik
| Metod | Endpoint | Açıklama |
|---|---|---|
| GET | `/api/v1/analytics/distance` | İki nokta arası mesafe |
| GET | `/api/v1/analytics/nearby` | Yarıçap içindeki cihazlar |

#### WebSocket
| Endpoint | Açıklama |
|---|---|
| `ws://host/ws` | WebSocket bağlantı noktası |
| `/topic/locations` | Canlı konum güncellemeleri |

### Testler

JUnit 5 ile yazılmış 19 birim ve entegrasyon testi.

| Test Sınıfı | Tür | Ne Test Ediyor |
|---|---|---|
| `DeviceDaoTest` | Repository | Kaydetme, duplicate kod, findByCode, existsByCode, durum güncelleme |
| `LocationDaoTest` | Repository | Konum kaydetme, cihaza göre listeleme, son konum bulma |
| `DeviceServiceTest` | Service | CRUD işlemleri, hata fırlatma, DTO dönüşümleri |

```bash
mvn test
# Tests run: 19, Failures: 0, Errors: 0
```

### Kurulum

```bash
# 1. Repoyu klonla
git clone https://github.com/sedabasaran/smart-fleet-tracking.git
cd smart-fleet-tracking

# 2. Veritabanı oluştur
psql -U postgres -c "CREATE DATABASE fleet_tracking;"

# 3. application.properties güncelle
spring.datasource.url=jdbc:postgresql://localhost:5432/fleet_tracking
spring.datasource.username=kullanici_adi
spring.datasource.password=sifre

# 4. Çalıştır
mvn spring-boot:run

# 5. Harita arayüzüne eriş
# http://localhost:8080
```

### Simülasyon

Uygulama başladığında 5 araç otomatik oluşturulur ve her 5 saniyede bir konum günceller. Harita arayüzünden gerçek zamanlı takip edebilirsiniz.

---

## English

### Project Description

Real-time vehicle and device tracking backend system built with Java 17 and Spring Boot 3.2. Features live location streaming via WebSocket, GIS module, JWT authentication, and event-driven architecture.

### Features

- **JWT Authentication** — Role-based authorization (ADMIN / VIEWER)
- **Device Management** — Full CRUD operations
- **Real-Time Location Tracking** — Live data stream via WebSocket
- **GIS Module:**
  - Distance calculation using Haversine formula
  - Heading (bearing angle) calculation
  - Find devices within a given radius (radius query)
- **Event-Driven Architecture** — WebSocket broadcast via Spring Events
- **Simulation Engine** — Auto-generates location data every 5 seconds (5 vehicles)
- **Map Interface** — Real-time vehicle tracking with Leaflet.js
- **Global Exception Handling** — Consistent error responses
- **CI/CD** — Automated build and test with GitHub Actions

### Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.2 |
| Security | Spring Security, JWT |
| ORM | Spring Data JPA / Hibernate |
| Database | PostgreSQL |
| Real-Time | WebSocket (STOMP) |
| Frontend | Leaflet.js |
| Testing | JUnit 5, MockMvc |
| CI/CD | GitHub Actions |
| Utilities | Lombok, Jackson |

### Architecture

```
controller/     → REST API endpoints
service/        → Business logic
repository/     → Database access
entity/         → Database table mappings
dto/            → Request / Response objects
mapper/         → Entity ↔ DTO conversions
event/          → Spring Event classes
listener/       → WebSocket broadcast listeners
websocket/      → WebSocket configuration
security/       → JWT filter and configuration
simulation/     → Vehicle simulation engine
exception/      → Global exception handling
```

### API Endpoints

#### Authentication
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/v1/auth/register` | Register new user |
| POST | `/api/v1/auth/login` | Login, returns JWT token |

#### Device Management
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/v1/devices` | Create new device |
| GET | `/api/v1/devices` | List all devices |
| GET | `/api/v1/devices/{id}` | Get device by ID |
| PUT | `/api/v1/devices/{id}` | Update device |
| DELETE | `/api/v1/devices/{id}` | Delete device |

#### Location Tracking
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/v1/locations` | Add location record |
| GET | `/api/v1/locations/device/{id}` | Device location history |
| GET | `/api/v1/locations/device/{id}/latest` | Latest location |

#### Analytics
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/v1/analytics/distance` | Distance between two points |
| GET | `/api/v1/analytics/nearby` | Devices within radius |

#### WebSocket
| Endpoint | Description |
|---|---|
| `ws://host/ws` | WebSocket connection |
| `/topic/locations` | Live location updates |

### Tests

19 unit and integration tests written with JUnit 5.

| Test Class | Type | What it tests |
|---|---|---|
| `DeviceDaoTest` | Repository | Save, duplicate code, findByCode, existsByCode, status update |
| `LocationDaoTest` | Repository | Save location, list by device, find latest |
| `DeviceServiceTest` | Service | CRUD operations, exception throwing, DTO mapping |

```bash
mvn test
# Tests run: 19, Failures: 0, Errors: 0
```

### Setup

```bash
# 1. Clone the repo
git clone https://github.com/sedabasaran/smart-fleet-tracking.git
cd smart-fleet-tracking

# 2. Create database
psql -U postgres -c "CREATE DATABASE fleet_tracking;"

# 3. Update application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/fleet_tracking
spring.datasource.username=your_username
spring.datasource.password=your_password

# 4. Run
mvn spring-boot:run

# 5. Open map interface
# http://localhost:8080
```

### Simulation

On startup, 5 vehicles are automatically created and update their location every 5 seconds. Track them in real time via the map interface.


