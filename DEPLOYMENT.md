# SpotNShop Deployment Guide

## Deploy to Render

### Option 1: Using render.yaml (Recommended)

1. **Push to GitHub:**
   ```bash
   git add .
   git commit -m "Prepare for Render deployment"
   git push origin main
   ```

2. **Connect to Render:**
   - Go to [render.com](https://render.com)
   - Sign up/Login with GitHub
   - Click "New" → "Blueprint"
   - Connect your GitHub repository
   - Render will automatically detect `render.yaml`

3. **Environment Variables:**
   Render will automatically set up:
   - `DATABASE_URL` (from PostgreSQL database)
   - `SPRING_DATASOURCE_USERNAME`
   - `SPRING_DATASOURCE_PASSWORD`
   - `SPRING_PROFILES_ACTIVE=production`

### Option 2: Manual Web Service

1. **Create PostgreSQL Database:**
   - Go to Render Dashboard
   - Click "New" → "PostgreSQL"
   - Name: `spotnshop-db`
   - Plan: Free

2. **Create Web Service:**
   - Click "New" → "Web Service"
   - Connect GitHub repository
   - Build Command: `mvn clean package -DskipTests`
   - Start Command: `java -jar target/spotnshop-app-1.0.0.jar`
   - Environment: Java

3. **Set Environment Variables:**
   ```
   SPRING_PROFILES_ACTIVE=production
   DATABASE_URL=[Copy from PostgreSQL service]
   PORT=8080
   ```

### Test Accounts (Production)
- **Admin:** admin / admin123
- **Shopkeeper:** shopkeeper1 / shop123
- **Customer:** customer1 / customer123

### Features Available
- ✅ User Authentication & Authorization
- ✅ Offer Management (Create, View, Edit, Delete)
- ✅ Search & Filter Functionality
- ✅ Favorites System
- ✅ Profile Management
- ✅ Admin Panel
- ✅ Responsive Design

### URLs After Deployment
- **Application:** https://your-app-name.onrender.com
- **Login:** https://your-app-name.onrender.com/login
- **Admin:** https://your-app-name.onrender.com/admin/dashboard

## Local Development
```bash
mvn spring-boot:run
```
Access at: http://localhost:8081