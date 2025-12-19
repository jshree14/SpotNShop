# Deploy SpotNShop on Railway

## Why Railway?
- ✅ **Free Tier:** $5 credit monthly (enough for small apps)
- ✅ **Java Support:** Native Spring Boot support
- ✅ **Free PostgreSQL:** Included in free tier
- ✅ **Easy Setup:** Git-based deployment
- ✅ **Custom Domains:** Free subdomain + custom domain support

## Step-by-Step Deployment

### 1. Prepare Your Code
```bash
git add .
git commit -m "Prepare for Railway deployment"
git push origin main
```

### 2. Deploy on Railway

1. **Sign Up:**
   - Go to [railway.app](https://railway.app)
   - Sign up with GitHub

2. **Create New Project:**
   - Click "New Project"
   - Select "Deploy from GitHub repo"
   - Choose your SpotNShop repository

3. **Add PostgreSQL Database:**
   - In your project dashboard
   - Click "New" → "Database" → "Add PostgreSQL"
   - Railway will create a database automatically

4. **Configure Environment Variables:**
   Railway will auto-detect and set:
   - `DATABASE_URL` (from PostgreSQL service)
   - `PGHOST`, `PGPORT`, `PGUSER`, `PGPASSWORD`, `PGDATABASE`

   Add these manually:
   ```
   SPRING_PROFILES_ACTIVE=production
   PORT=8080
   ```

### 3. Custom Domain (Optional)
- Go to project settings
- Add custom domain or use Railway subdomain
- Format: `your-app-name.up.railway.app`

### 4. Monitor Deployment
- Check build logs in Railway dashboard
- Application will be available at your Railway URL

## Environment Variables Reference
```bash
# Automatically set by Railway
DATABASE_URL=postgresql://user:pass@host:port/db
PGHOST=host
PGPORT=5432
PGUSER=postgres
PGPASSWORD=password
PGDATABASE=railway

# Set manually
SPRING_PROFILES_ACTIVE=production
PORT=8080
```

## Test Accounts (Production)
- **Admin:** admin / admin123
- **Shopkeeper:** shopkeeper1 / shop123
- **Customer:** customer1 / customer123

## Troubleshooting

### Build Fails
- Check Java version in `system.properties`
- Ensure Maven wrapper is executable

### Database Connection Issues
- Verify `DATABASE_URL` is set
- Check PostgreSQL service is running

### Application Won't Start
- Check logs in Railway dashboard
- Verify `PORT` environment variable

## Cost Estimate
- **Free Tier:** $5 credit/month
- **Typical Usage:** $2-3/month for small app
- **Includes:** Web service + PostgreSQL database