# Smart Fleet Tracking System

Araç ve cihazların gerçek zamanlı takibini sağlayan backend sistemi. Java 17 ve Spring Boot ile geliştirildi.

## Özellikler

- JWT kimlik doğrulama ve role bazlı yetkilendirme (ADMIN / VIEWER)
- Cihaz yönetimi — CRUD işlemleri
- Gerçek zamanlı konum takibi — WebSocket ile canlı veri akışı
- GIS modülü:
  - Haversine formülü ile iki nokta arası mesafe hesabı
  - Heading (yön açısı) hesaplama
  - Belirli yarıçap içindeki cihazları bulma (radius query)
- Event-driven mimari — Spring Events ile WebSocket broadcast
- Çok araçlı simülasyon sistemi (5 saniyede bir konum üretir)
- Global exception handling
- Harita arayüzü — Leaflet.js ile gerçek zamanlı araç takibi
- GitHub Actions CI/CD

## Teknolojiler

Java 17 · Spring Boot 3.2 · Spring Security · Spring Data JPA · PostgreSQL · WebSocket · Lombok · JWT


https://github.com/user-attachments/assets/7cbf6a15-797d-4e76-81f4-830bcdf5df20

![Uploading Ekran Resmi 2026-05-14 00.02.41.png…]()


## Mimari

```
Controller → Service → Repository → PostgreSQL
                ↓
         Event Publisher
                ↓
       WebSocket Broadcast → Leaflet.js Harita
```

## Kurulum

**Gereksinimler:** Java 17, PostgreSQL, Maven

```bash
# 1. PostgreSQL'de database oluştur
CREATE DATABASE fleettracking;

# 2. application.properties dosyasında şifreyi gir
spring.datasource.password=senin_sifren

# 3. Projeyi başlat
mvn spring-boot:run
```

Uygulama `http://localhost:8080` adresinde çalışır.

## API Endpoints

### Auth

| Method | URL | Açıklama | Auth |
|--------|-----|----------|------|
| POST | `/api/v1/auth/register` | Kullanıcı kaydı | ❌ |
| POST | `/api/v1/auth/login` | Giriş → JWT token döner | ❌ |

**Register:**
```json
POST /api/v1/auth/register
{
  "username": "admin",
  "password": "123456",
  "email": "admin@test.com",
  "role": "ADMIN"
}
```

**Login:**
```json
POST /api/v1/auth/login
{
  "username": "admin",
  "password": "123456"
}
```

---

### Devices

| Method | URL | Açıklama | Yetki |
|--------|-----|----------|-------|
| POST | `/api/v1/devices` | Cihaz ekle | ADMIN |
| GET | `/api/v1/devices` | Tüm cihazlar | ADMIN, VIEWER |
| GET | `/api/v1/devices/{id}` | Cihaz detay | ADMIN, VIEWER |
| PUT | `/api/v1/devices/{id}` | Güncelle | ADMIN |
| DELETE | `/api/v1/devices/{id}` | Sil | ADMIN |

**Cihaz ekle:**
```json
POST /api/v1/devices
Authorization: Bearer <token>

{
  "deviceCode": "TRK-001",
  "name": "Araç 1",
  "type": "VEHICLE",
  "status": "ACTIVE"
}
```

**Status değerleri:** `ACTIVE` · `INACTIVE` · `MAINTENANCE`

---

### Locations

| Method | URL | Açıklama | Yetki |
|--------|-----|----------|-------|
| POST | `/api/v1/locations` | Konum kaydet | ADMIN |
| GET | `/api/v1/locations/device/{id}/last` | Son konum | ADMIN, VIEWER |
| GET | `/api/v1/locations/device/{id}/history` | Konum geçmişi | ADMIN, VIEWER |
| GET | `/api/v1/locations/nearby` | Yakındaki cihazlar (GIS) | ADMIN, VIEWER |

**Konum kaydet:**
```json
POST /api/v1/locations
Authorization: Bearer <token>

{
  "deviceId": 1,
  "latitude": 41.0082,
  "longitude": 28.9784,
  "speed": 65.5,
  "heading": 90.0,
  "altitude": 50.0
}
```

**Yakındaki cihazlar:**
```
GET /api/v1/locations/nearby?lat=41.0082&lon=28.9784&radiusKm=10
Authorization: Bearer <token>
```

---

### Analytics (GIS)

| Method | URL | Açıklama | Yetki |
|--------|-----|----------|-------|
| GET | `/api/v1/analytics/distance` | İki nokta arası mesafe (km) | ADMIN, VIEWER |
| GET | `/api/v1/analytics/heading` | Yön açısı (0-360°) | ADMIN, VIEWER |

**Mesafe hesapla — İstanbul → Ankara:**
```
GET /api/v1/analytics/distance?lat1=41.0082&lon1=28.9784&lat2=39.9334&lon2=32.8597

Sonuç: 349.2 km
```

**Yön açısı:**
```
GET /api/v1/analytics/heading?lat1=41.0082&lon1=28.9784&lat2=39.9334&lon2=32.8597

Sonuç: 122.4° (güneydoğu)
```

---

### WebSocket

```
ws://localhost:8080/ws/locations
```

Bağlandıktan sonra her yeni konum kaydında otomatik mesaj gelir:

```json
{
  "deviceCode": "TRK-001",
  "lat": 41.0082,
  "lon": 28.9784,
  "speed": 65.5,
  "recordedAt": "2025-05-10T17:30:00"
}
```

---

## GIS Detayı

Haversine formülü ile küre yüzeyinde mesafe hesabı — kütüphane kullanılmadı, saf Java implementasyonu.

```java
// İki koordinat arası mesafe (km)
double distance = geoService.calculateDistance(lat1, lon1, lat2, lon2);

// Yön açısı (0-360°)
double heading = geoService.calculateHeading(lat1, lon1, lat2, lon2);

// Radius içindeki cihazlar
List<NearbyDeviceResponseDto> nearby = locationService.findNearbyDevices(lat, lon, radiusKm);
```

## Postman

`SmartFleetTracking.postman_collection.json` dosyasını Postman'a import et.
Login sonrası token otomatik set edilir, diğer isteklerde manuel girmene gerek kalmaz.# Smart Fleet Tracking System

Java 17 ve Spring Boot kullanılarak geliştirilen, WebSocket destekli gerçek zamanlı araç ve cihaz takip backend sistemi.
##  Özellikler

-  JWT kimlik doğrulama ve yetkilendirme (Spring Security)
-  Cihaz (Device) CRUD yönetimi
-  Gerçek zamanlı konum takibi — WebSocket ile canlı veri akışı
-  Event-driven mimari (Spring Events → WebSocket broadcast)
-  GIS — Coğrafi Hesaplamalar:
  - Haversine formülü ile iki nokta arası mesafe (km)
  - Heading (yön açısı) hesaplama 0–360°
  - Belirli yarıçap içindeki cihazları bulma (Radius Query)
  - Bounding box ön filtresi (performans optimizasyonu)
-  Konum simülasyon sistemi (İstanbul bazlı, 5 sn aralıklı)
-  Global exception handling (@RestControllerAdvice)
-  Docker & docker-compose
-  GitHub Actions CI/CD

##  Mimari

Client
  │
  ▼
Controller Layer   ← REST API + Validation
  │
  ▼
Service Layer      ← Business Logic + Event Publishing
  │         │
  ▼         ▼
Repository  GeoService (Haversine / Heading / BoundingBox)
  │
  ▼
PostgreSQL
  │
  ▼ (Event)
WebSocket Handler  → Bağlı tüm client'lara broadcast

##  GIS Endpoint'leri

| Method | Endpoint | Açıklama |
|--------|----------|----------|
| GET | `/api/v1/locations/nearby?lat=41.0&lon=28.9&radiusKm=5` | Yakındaki cihazlar |
| GET | `/api/v1/analytics/distance?lat1=41.0&lon1=28.9&lat2=41.1&lon2=29.0` | Mesafe (km) |
| GET | `/api/v1/analytics/heading?lat1=41.0&lon1=28.9&lat2=41.1&lon2=29.0` | Yön açısı (°) |

##  Tüm Endpoint'ler

| Method | Endpoint | Auth | Açıklama |
|--------|----------|------|----------|
| POST | `/api/v1/auth/register` |  Kayıt |
| POST | `/api/v1/auth/login` |  Giriş → JWT |
| POST | `/api/v1/devices` |  Cihaz ekle |
| GET | `/api/v1/devices` | Tüm cihazlar |
| GET | `/api/v1/devices/{id}` |  Cihaz detay |
| PUT | `/api/v1/devices/{id}` |  Güncelle |
| DELETE | `/api/v1/devices/{id}` |  Sil |
| POST | `/api/v1/locations` |  Konum kaydet |
| GET | `/api/v1/locations/device/{id}/last` |  Son konum |
| GET | `/api/v1/locations/device/{id}/history` |   Konum geçmişi |
| GET | `/api/v1/locations/nearby` | Yakındaki cihazlar |
| GET | `/api/v1/analytics/distance` |  Mesafe hesapla |
| GET | `/api/v1/analytics/heading` |  Yön hesapla |
| WS | `ws://localhost:8080/ws/locations` | Canlı konum akışı |

##  Çalıştırma

### Local
bash
# PostgreSQL'de fleettracking database oluştur
# application.properties'te şifreni gir
mvn spring-boot:run

##  Teknoloji Stack

| Kategori | Teknoloji |
|----------|-----------|
| Dil | Java 17 |
| Framework | Spring Boot 3.2 |
| Güvenlik | Spring Security + JWT |
| ORM | Spring Data JPA + Hibernate |
| Veritabanı | PostgreSQL |
| Gerçek Zamanlı | WebSocket |
| Mimari | Katmanlı + Event-driven |
| DevOps | Docker, GitHub Actions |
| Build | Maven |
