docker run -d \
  --name redis-stack \
  -v $(pwd)/redis-stack.conf:/redis-stack.conf \
  -v $(pwd)/data:/data \
  -p 6379:6379 \
  -p 8001:8001 \
  redis/redis-stack:latest

