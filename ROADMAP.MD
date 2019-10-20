# Misc To dos

- ( ) Fix movies and actors example
- ( ) Impl arangodb's UPDATE functionality as DSL

# Milestones

## LOGG - Logging improvements

- ( ) karango appender
- ( ) FingerCrossed appender like in symfony
- ( ) sentry.io appender (sending loggs to sentry when 500 error: https://sentry.io/for/kotlin/)

## GLOL - Global Locks

Idea: This brings the ability to globally lock any resource

Dependencies: None

- ( ) Interface for GlobalLockStorage
- ( ) acquire (with lock-ttl and wait-timeout)
- ( ) release

- ( ) Karango specific implementation

## BAJO - Background Jobs

Idea: Register and execute background jobs (short or long running jobs)

Dependencies: GLOL

- ( ) Common interfaces
  - ( ) Job definition
  - ( ) job storage
  - ( ) result storage
- ( ) register jobs
  - ( ) one shot
  - ( ) recurring

- ( ) Karango specific implementation

## HEAL - Health Checks

Idea: Plugable health check architecture

Dependencies: none (ktorfx)

- ( ) Common interfaces
  - ( ) result storage
  - ( ) health api-endpoint so one could check via StatusCake or similar
- ( ) health check runner
- ( ) history
- ( ) dashboard for presenting the history

## MESS - messaging

Idea: Send message over different channels like email, sms, ...

Dependencies: none

- ( ) Common interfaces
  - ( ) for email messages
  - ( ) for sms messages

- sendgrid specific implementation for email
- nexmo specific implementation for sms

## PESO - Persistent sessions

Idea: we need session that are not stored in cookies

## USER - User management

Idea: reusable users management e.g. for customers or admin users

Dependencies: PESO?, MESS / ktorfx

- ( ) Common interfaces
  - ( ) minimal user object
  - ( ) user storage
  - ( ) login, logout 
  - ( ) password reset (needs MESS)
- ( ) login with google (when enabled)
- ( ) login with facebook (when enabled)
- ( ) ui for user management / password reset etc. (customer)
- ( ) ui for user management (admin)

## FIST - File storage

Idea: Storing and loading files in different locations / remote services

Dependencies: none

- (x) common interfaces for a file storage, repository, bucket, file
- (x) File system repository
- ( ) encryption for names and content
- ( ) signing of content to ensure data is consistent

## RECO - Request Container

Idea: A minimal DI container that lives for only on ktor-call

Dependencies: none

- (x) implement a DI that lazily instantiates requested services
- (x) consistency check for container (running in dev mode)
  - (x) are all dependencies present for all services
  - ( ) detect cyclic dependencies

## INSI - Insights 

Idea: Architectural Improvements 

- ( ) remove direct dependency to file depot
- ( ) introduce a insight repository interface
- ( ) example implementation of file depot repository
