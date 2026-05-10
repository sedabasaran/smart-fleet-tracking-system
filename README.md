# Smart Fleet Tracking System

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
