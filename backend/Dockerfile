# Step 1: Build stage
FROM node:20-alpine AS builder

WORKDIR /usr/src/app

# Copy package configuration files
COPY package*.json ./

# Install dependencies
RUN npm ci

# Copy application source code
COPY . .

# Step 2: Production stage
FROM node:20-alpine

WORKDIR /usr/src/app

# Set environment variables
ENV NODE_ENV=production

# Copy only the necessary files and node_modules from the builder stage
COPY --from=builder /usr/src/app .

# Expose the port (Render sets this dynamically, backend defaults to 5000)
EXPOSE 5000

# Start the application
CMD ["npm", "start"]
