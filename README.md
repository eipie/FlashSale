# Backend Web Development Project for E-Commerce System

### Overview
- This is a personal web development project on building the backend for a e-commerce system. Specifically, this project aims to establish the fundamentals of a performant system for handling highly-concurrent user requests during a flash sale event such as Black Friday and Boxing Day.

### Design
- Several design decision has been made to migitate common issues found in such use case, including:
    - the use of a database cache (Redis) to store frequently accessed items in database
    - a flow control service that prevents high-volume requests from overwhelming critical systems
    - delay messaging using RocketMQ to ensure data consistency between services, asynchronously process events, and decouple components
    - data validation and Lua scripting for enforcing purchase quantity limitations (x units per user) and preventing overselling

### Technologies used
- framework: Spring
- database: MySQL
- cache: Redis
- message queue: RocketMQ
- flow control: Apache Sentinel
