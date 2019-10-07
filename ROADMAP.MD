# Misc To dos

- ( ) Fix movies and actors example
- ( ) Impl arangodb's UPDATE functionality as DSL

# Milestones

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

## INSIGHTS - beautify collector view in gui

- ( ) Overview ... some fancy display
- ( ) VaultCollector
- ( ) Request 
- ( ) Response 
