# SpotNShop - Advanced Local Shop Offers Platform

SpotNShop is a comprehensive Java Spring Boot application that connects local shopkeepers with customers through an advanced offers and deals platform. The application features real-time analytics, premium accounts, social features, and robust PostgreSQL integration.

## 🚀 Features

### For Shopkeepers
- **Offer Management**: Create, edit, and manage product offers with images
- **Premium Accounts**: Subscribe to premium plans for enhanced features
- **Analytics Dashboard**: Track offer views, clicks, and engagement metrics
- **Image Upload**: Add attractive images to offers for better visibility
- **Real-time Notifications**: Get notified about offer approvals and customer interactions
- **Chat System**: Communicate directly with interested customers

### For Customers
- **Advanced Search**: Browse offers by category, city, price range, and offer type
- **Favorites System**: Save and manage favorite offers for quick access
- **Rating & Reviews**: Rate offers and leave detailed reviews
- **Social Features**: Chat with shopkeepers about offers
- **Personalized Dashboard**: View personalized offer recommendations
- **Notification Preferences**: Customize notification settings

### For Administrators
- **Comprehensive Dashboard**: Monitor platform activity with detailed analytics
- **User Management**: Manage all user accounts and roles
- **Offer Moderation**: Review, approve, or reject offers with admin comments
- **Analytics & Reporting**: Access detailed platform usage statistics
- **Premium Account Management**: Monitor premium subscriptions and revenue

## 🛠️ Technology Stack

- **Backend**: Java 21, Spring Boot 3.2.0
- **Database**: PostgreSQL 18 with persistent data storage
- **Security**: Spring Security 6 with role-based access control
- **Frontend**: Thymeleaf, Bootstrap 5, Font Awesome icons
- **Build Tool**: Maven 3.6+
- **ORM**: Hibernate/JPA with automatic schema generation
- **File Upload**: Spring Multipart with configurable size limits

## 🚀 Deployment

### Live Application
Deploy your SpotNShop application on Railway for free!

**Railway Deployment (Recommended)**
1. **Sign Up**: Go to [railway.app](https://railway.app) and sign up with GitHub
2. **Create Project**: Click "New Project" → "Deploy from GitHub repo" → Select SpotNShop
3. **Add Database**: Click "New" → "Database" → "Add PostgreSQL" 
4. **Set Environment Variables**:
   ```
   SPRING_PROFILES_ACTIVE=production
   PORT=8080
   ```
5. **Deploy**: Railway automatically builds and deploys your app
6. **Access**: Your app will be available at `your-app-name.up.railway.app`

### Local Development

#### Prerequisites
- Java 17 or higher
- Maven 3.6+

#### Running Locally
1. Clone the repository
2. Navigate to the project directory
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
4. Open your browser and go to `http://localhost:8081`

### Default Users

The application comes with pre-configured test users (no sample offers are created):

- **Admin**: `admin` / `admin123`
- **Shopkeeper**: `shopkeeper1` / `shop123`  
- **Customer**: `customer1` / `customer123`

**Note**: The application starts with only user accounts. All offers must be created by shopkeepers through the web interface.

### Database Configuration

The application uses PostgreSQL as the primary database.

#### PostgreSQL Setup
1. Install PostgreSQL on your system
2. Run the database setup script:
   ```sql
   psql -U postgres -f database-setup.sql
   ```
3. The application will connect to PostgreSQL automatically

#### Database Access
- **Database**: `spotnshop`
- **Username**: `spotnshop_user`
- **Password**: `spotnshop_password`
- **Port**: 5432

You can monitor the database using pgAdmin or any PostgreSQL client.

## 📁 Application Structure

```
src/main/java/com/spotnshop/
├── model/          # Entity classes
│   ├── User.java           # User entity with roles
│   ├── Offer.java          # Offer entity with analytics
│   ├── Analytics.java      # Analytics tracking
│   ├── PremiumAccount.java # Premium subscriptions
│   ├── Rating.java         # Rating and review system
│   ├── Favorite.java       # User favorites
│   ├── ChatMessage.java    # Chat messaging
│   └── enums/              # Enumerations
├── repository/     # Data access layer (JPA repositories)
├── service/        # Business logic services
│   ├── UserService.java
│   ├── OfferService.java
│   ├── AnalyticsService.java
│   ├── PremiumAccountService.java
│   └── ...
├── controller/     # Web controllers
│   ├── HomeController.java
│   ├── AdminController.java
│   ├── ShopkeeperController.java
│   ├── CustomerController.java
│   ├── AnalyticsController.java
│   ├── PremiumController.java
│   └── ...
├── config/         # Configuration classes
└── SpotNShopApplication.java
```

## 📊 Key Models & Features

### User Roles & Permissions
- **ADMIN**: Full platform access, user management, analytics
- **SHOPKEEPER**: Offer management, premium features, analytics dashboard
- **CUSTOMER**: Browse offers, favorites, ratings, chat

### Offer Categories
- Cosmetics, Food, Fruits, Clothing, Grocery
- Cleaning Equipment, Electronics, Books, Sports, Other

### Offer Types
- **DISCOUNT**: Fixed amount discounts
- **PERCENTAGE_OFF**: Percentage-based discounts
- **BOGO**: Buy One Get One offers
- **FREEBIE**: Free items or services
- **MINIMUM_PURCHASE**: Minimum purchase requirements
- **SPECIAL**: Special promotional offers

### Premium Plans
- **BASIC**: ₹299/30 days - Enhanced visibility, basic analytics
- **PREMIUM**: ₹599/30 days - Priority listing, advanced analytics, chat support
- **ENTERPRISE**: ₹999/30 days - All features, dedicated support, custom branding

### Analytics Tracking
- Offer views and clicks
- Search queries and patterns
- User engagement metrics
- Favorite additions and removals
- Rating submissions
- Chat message analytics

## 🌐 API Endpoints

### Public Access
- `GET /` - Home page with featured offers
- `GET /login` - User authentication
- `GET /register` - User registration
- `GET /offers/{id}` - View offer details

### Admin Dashboard
- `GET /admin/dashboard` - Comprehensive admin overview
- `GET /admin/offers` - Manage all offers with bulk actions
- `GET /admin/users` - User management and statistics
- `POST /admin/offers/{id}/approve` - Approve offers
- `POST /admin/offers/{id}/reject` - Reject offers with comments

### Shopkeeper Features
- `GET /shopkeeper/dashboard` - Analytics and offer overview
- `GET /shopkeeper/offers/new` - Create offers with image upload
- `GET /shopkeeper/offers` - Manage personal offers
- `GET /analytics/dashboard` - Detailed analytics dashboard
- `GET /premium` - Premium plan management

### Customer Features
- `GET /customer/dashboard` - Personalized customer dashboard
- `GET /favorites` - Manage favorite offers
- `POST /ratings` - Submit ratings and reviews
- `GET /profile` - Profile management and notifications

### Advanced Features
- `GET /analytics/api/stats` - Real-time analytics API
- `POST /premium/subscribe` - Premium subscription management
- `GET /chat` - Chat system for customer-shopkeeper communication
- `POST /upload/image` - Image upload for offers

## 🔧 Development Notes

### Database Configuration
- **Production**: PostgreSQL with persistent data storage
- **Development**: H2 in-memory database for rapid development
- **Auto Schema**: Hibernate automatically creates/updates database schema
- **Data Initialization**: Sample data loaded on startup via DataInitializer

### Security Features
- **Role-based Access Control**: Different permissions for Admin/Shopkeeper/Customer
- **Password Encryption**: BCrypt password hashing
- **Session Management**: Secure session handling
- **CSRF Protection**: Cross-site request forgery protection

### Performance Optimizations
- **Database Indexing**: Optimized queries with proper indexing
- **Lazy Loading**: Efficient entity loading strategies
- **Connection Pooling**: HikariCP for database connection management
- **Caching**: Thymeleaf template caching (disabled in development)

### File Management
- **Image Upload**: Configurable file size limits (10MB default)
- **File Storage**: Local file system storage with organized directory structure
- **Image Processing**: Automatic image handling for offers

## ✨ Advanced Features Implemented

### Analytics System
- **Real-time Tracking**: User interactions, page views, clicks
- **Dashboard Visualization**: Charts and statistics for business insights
- **Performance Metrics**: Offer performance tracking and optimization suggestions

### Premium Account System
- **Subscription Management**: Multiple plan tiers with different features
- **Payment Integration Ready**: Structure prepared for payment gateway integration
- **Feature Gating**: Premium features accessible only to subscribed users

### Social Features
- **Rating & Review System**: 5-star rating with detailed reviews
- **Favorites Management**: Personal offer collections
- **Chat System**: Direct communication between customers and shopkeepers
- **Notification System**: Customizable notification preferences

### Search & Discovery
- **Advanced Search**: Multi-criteria search with filters
- **Category Browsing**: Organized offer discovery
- **Location-based Results**: City-wise offer filtering
- **Price Range Filtering**: Budget-based offer discovery

## 🚦 Application Status

### ✅ Fully Implemented Features
- **User Management**: Registration, authentication, role-based access
- **Offer Management**: CRUD operations with image upload
- **Admin Panel**: Complete administrative functionality
- **Analytics System**: Real-time tracking and dashboard
- **Premium Accounts**: Subscription management system
- **Rating & Reviews**: Customer feedback system
- **Favorites**: Personal offer collections
- **Chat System**: Customer-shopkeeper communication
- **Search & Filtering**: Advanced search capabilities
- **Database Integration**: PostgreSQL with H2 fallback
- **Security**: Comprehensive Spring Security implementation

### 🔄 Ready for Enhancement
- **Payment Gateway Integration**: Structure ready for Razorpay/Stripe
- **Email Notifications**: Service layer prepared for SMTP integration
- **Mobile API**: RESTful endpoints ready for mobile app development
- **Geolocation**: Location-based services integration ready
- **Social Media Integration**: Share offers on social platforms

## 📈 Performance Metrics

- **Database**: 8 main entities with optimized relationships
- **Controllers**: 12+ controllers handling all user interactions
- **Services**: 8+ service classes with business logic
- **Templates**: 20+ responsive Thymeleaf templates
- **Security**: Role-based access with 3 user types
- **File Upload**: Configurable limits with organized storage

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is open source and available under the MIT License.

## 📞 Support

For support and questions:
- Create an issue in the GitHub repository
- Check the application logs for debugging information
- Review the PostgreSQL database for data verification

---

**SpotNShop** - Connecting local businesses with customers through innovative technology! 🛍️