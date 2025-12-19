# Free Database Options for SpotNShop

## 1. Supabase (Recommended)
- **Free Tier:** 500MB PostgreSQL database
- **Setup:** 
  1. Go to [supabase.com](https://supabase.com)
  2. Create new project
  3. Get connection string from Settings → Database
  4. Use in environment variables

## 2. PlanetScale
- **Free Tier:** 1GB MySQL database
- **Setup:**
  1. Go to [planetscale.com](https://planetscale.com)
  2. Create database
  3. Get connection string
  4. Update application.properties for MySQL

## 3. Neon
- **Free Tier:** 512MB PostgreSQL database
- **Setup:**
  1. Go to [neon.tech](https://neon.tech)
  2. Create database
  3. Get connection string

## 4. ElephantSQL
- **Free Tier:** 20MB PostgreSQL database
- **Setup:**
  1. Go to [elephantsql.com](https://elephantsql.com)
  2. Create "Tiny Turtle" free instance
  3. Get connection URL

## Environment Variables for Deployment
```bash
DATABASE_URL=postgresql://username:password@host:port/database
DB_USERNAME=your_username
DB_PASSWORD=your_password
SPRING_PROFILES_ACTIVE=production
```

## For MySQL (PlanetScale)
Update pom.xml to include MySQL driver:
```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
```

Update application.properties:
```properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```