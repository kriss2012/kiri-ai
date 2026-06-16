# Step 1: Build stage
FROM node:20-alpine AS builder

WORKDIR /usr/src/app

# Copy package files from backend
COPY backend/package*.json ./backend/

# Install dependencies
RUN cd backend && npm ci

# Copy backend source code
COPY backend ./backend

# Step 2: Production stage
FROM node:20-alpine

WORKDIR /usr/src/app

# Set environment variables
ENV NODE_ENV=production

# Copy only the necessary files and node_modules from the builder stage
COPY --from=builder /usr/src/app .

# Expose port (backend defaults to 5000)
EXPOSE 5000

# Start the application
CMD ["node", "backend/server.js"]
