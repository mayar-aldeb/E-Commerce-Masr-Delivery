# Masr Delivery

A food delivery backend built with **Java and Spring Boot**. It covers the full life of an order: customers browse restaurants and place orders, restaurants accept and prepare them, riders pick up and deliver, and admins manage the platform and read reports.

> Status: work in progress. Data is stored **in memory** (it resets on restart). Spring Data JPA with a database, Spring Security and automated tests are planned next.

## Features

- **Customers:** browse and search restaurants, filter them, view menu items, place an order (with an optional promotion code), pay, cancel, track order status, view order history.
- **Restaurants:** accept, prepare and mark orders ready, manage menu items, combos and stock, mark items available or unavailable, list new orders.
- **Riders:** go on or off duty, get an assigned order, mark pickup and delivery, view personal delivery statistics.
- **Admin:** add restaurants, riders and customers, create promotions, list everything, remove restaurants.
- **Reports:** total revenue for a date range, top restaurants of a month, average order per district, orders per status, rider deliveries, most frequent menu item, peak hour, inactive customers.
- **Delivery distance:** a district is turned into coordinates through the OpenStreetMap Nominatim API (district + "Cairo, Egypt"), used for routing and distance.

## Tech stack

| Area | Tools |
| --- | --- |
| Language | Java |
| Framework | Spring Boot 4.1.2 (Spring MVC / REST) |
| Build | Maven |
| Validation | Spring Validation (Jakarta annotations) |
| Mapping | MapStruct |
| Boilerplate | Lombok |
| Other | Spring Boot Actuator, Thymeleaf starter, Datafaker |

## Architecture

The code follows a layered structure under `src/main/java/org/route`:

```
controller/   REST endpoints (Admin, Customer, Restaurant, Rider, Report, Login)
platform/     use-case facades per actor (AdminPlatform, CustomerPlatform, RestaurantPlatform, RiderPlatform)
service/      business logic (Order, Customer, Restaurant, Rider, Admin, Invoice, Geocoding, Routing, Distance)
repository/   in-memory data access
model/        domain objects (Order, Customer, Restaurant, Rider, items, promotions, order states)
dto/          request and response objects
mapper/       MapStruct mappers between models and DTOs
validation/   input validation for customer, restaurant and rider
exception/    custom exceptions and a global exception handler
config/       AppConfig and typed order properties
enums/        OrderStatus, CustomerLoyaltyTier, VehicleType
```

Request flow: `controller -> platform -> service -> repository`, with DTOs and mappers at the edges.

## Design patterns used

| Pattern | Where |
| --- | --- |
| State | Order lifecycle in `model/orderStatus` (Placed, Accept, Preparing, Ready, Assigned, OutForDelivery, Delivered, Cancel) |
| Factory | `model/Items/ItemFactory` creates standard, weighted and combo menu items |
| Composite-style items | `Combo`, `StandardItem`, `WeightedItem` share the `MenuItem` type |
| Strategy | Promotions in `model/orderPromotion` (fixed amount, subtotal discount, free delivery) |
| Observer / listener | Restaurant filters in `model/restaurntFilterObserver` (cuisine, district, price ceiling, rate) |
| Facade | `platform/*` classes expose one simple entry point per actor |
| Builder | Used for response and error objects |
| Dependency Injection | Constructor injection across controllers, platforms and services |

## Configuration

Delivery and service pricing is set in `src/main/resources/application.properties`:

```properties
order.delivery-free=3
order.delivery-base=15
order.delivery-fee-per-km=3
order.service-rate=0.1
```

## How to run

Requirements: the JDK version set in `pom.xml` (`java.version`) and Maven.

```bash
git clone https://github.com/mayar-aldeb/E-Commerce-Masr-Delivery.git
cd E-Commerce-Masr-Delivery
mvn spring-boot:run
```

The app starts on `http://localhost:8080` (Spring Boot default). Geocoding needs internet access.

## API overview

Paths are written as defined in the controllers.

**Admin** (`/admin`)

| Method | Path | Purpose |
| --- | --- | --- |
| POST | `/admin/newRestaurant` | Add a restaurant |
| DELETE | `/admin/removeRestaurant{id}` | Remove a restaurant |
| POST | `/admin/addRider` | Add a rider |
| POST | `/admin/addCustomer` | Add a customer |
| POST | `/admin/newPromotion` | Create a promotion |
| GET | `/admin/restaurants`, `/admin/riders`, `/admin/customers`, `/admin/promotions` | List each |

**Customer** (`/customer`)

| Method | Path | Purpose |
| --- | --- | --- |
| GET | `/customer/restaurants` | Browse restaurants with filters (list in request body) |
| GET | `/customer/restaurantsSearch?search=` | Search restaurants |
| GET | `/customer/menuItems{restaurantId}` | Menu items of a restaurant |
| POST | `/customer/newOrder` | Place an order |
| POST | `/customer/payOrder{orderId}` | Pay an order |
| GET | `/customer/orderStatus{orderId}` | Order status |
| POST | `/customer/cancelOrder{orderId}` | Cancel an order |
| GET | `/customer/customerOrders{customerId}` | Customer order history |

**Restaurant** (`/restaurant`)

| Method | Path | Purpose |
| --- | --- | --- |
| PATCH | `/restaurant/acceptOrder{orderId}`, `/prepareOrder{orderId}`, `/readyOrder{orderId}` | Move an order forward |
| GET | `/restaurant/newOrders{restaurantId}` | New orders |
| POST | `/restaurant/newMenuItem` | Add a menu item |
| DELETE | `/restaurant/removeMenuItem?restaurantId=&menuItemId=` | Remove a menu item |
| PATCH | `/restaurant/availableItem`, `/unavailableItem` | Toggle availability (`restaurantId`, `menuItemId`) |
| PATCH | `/restaurant/newStock?restaurantId=&menuItemId=&stock=` | Update stock |
| POST / DELETE | `/restaurant/newComboItem`, `/removeComboItem` | Manage combo contents |

**Rider** (`/rider`)

| Method | Path | Purpose |
| --- | --- | --- |
| PATCH | `/rider/onDuty{riderId}`, `/offDuty{riderId}` | Duty status |
| PATCH | `/rider/newOrder{riderId}` | Get an order assigned |
| GET | `/rider/assignedOrder{riderId}` | View assigned order |
| PATCH | `/rider/pickUp{riderId}`, `/deliverOrder{riderId}` | Update delivery progress |
| GET | `/rider/personalDeliveryStatistics{riderId}` | Rider statistics |

**Lookup** (`/main`): `GET /main/customers{id}`, `/main/restaurants{id}`, `/main/riders{id}`

**Reports** (`/report`): `GET /report/TotalRevenue?startDate=&endDate=`, `/topRestaurants?month=&year=`, `/ordersPerDistrict{district}`, `/filterRestaurants?rate=&completedOrders=`, `/ordersPerStatus`, `/ridersDeliveries`, `/frequentlyMenuItem`, `/ordersOfCustomer{id}`, `/peakHour`, `/notActiveCustomers`

### Example requests

Add a customer (validated: name required, phone must match `01[0125]` + 8 digits, balance positive):

```bash
curl -X POST http://localhost:8080/admin/addCustomer \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Sara Ahmed",
    "phone": "01012345678",
    "address": { "district": "Nasr City", "details": "Street 10, Building 4" },
    "balance": 500
  }'
```

Add a restaurant:

```bash
curl -X POST http://localhost:8080/admin/newRestaurant \
  -H "Content-Type: application/json" \
  -d '{ "name": "Koshary House", "district": "Maadi", "rate": 4.5 }'
```

Place an order:

```bash
curl -X POST http://localhost:8080/customer/newOrder \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 1,
    "restaurantId": 1,
    "deliveryAddress": { "district": "Nasr City", "details": "Street 10, Building 4" },
    "orderItems": [ { "itemId": 1, "quantity": 2 } ],
    "promotionCode": null
  }'
```

Restaurant accepts the order, then a rider picks it up:

```bash
curl -X PATCH http://localhost:8080/restaurant/acceptOrder1
curl -X PATCH http://localhost:8080/rider/pickUp1
```

Revenue report:

```bash
curl "http://localhost:8080/report/TotalRevenue?startDate=2026-11-01&endDate=2026-11-30"
```

Errors are returned in one format by the global exception handler:

```json
{
  "message": "...",
  "timestamp": "2026-11-24T13:00:00",
  "path": "/customer/payOrder1",
  "status": "NOT_FOUND",
  "error": "POST"
}
```

## Roadmap

- [ ] Spring Data JPA with a relational database (replace the in-memory repositories)
- [ ] Spring Security with JWT
- [ ] Unit and integration tests (JUnit, Mockito)
- [ ] OpenAPI / Swagger documentation
- [ ] Dockerfile

## Author

Mayar Mostafa Abdo, Junior Backend Developer. [LinkedIn](https://www.linkedin.com/in/mayar-mostafa-375112259) | Mayar.aldeb9@gmail.com
