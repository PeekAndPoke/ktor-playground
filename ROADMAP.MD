# Misc To dos

- ( ) Common key based registry  
  - ( ) Move class Key from ultra.vault to ultra.common
  - ( ) Create base impl of Registry in ultra.common   

- ( ) Fix movies and actors example
- ( ) Impl arangodb's UPDATE functionality as DSL

# Milestones

## KARA

Idea: Karango driver (arangodb) for Vault

Dependencies: none

- ( ) Remove DbEntityCollection and move all methods to EntityRepository
- ( ) Impl onSaveHooks in EntityRepository
- ( ) Impl EntityRepository::save(stored: Stored<T>)
- ( ) Impl Reference<T> (similar to Stored<T>) and remove @Ref annotation
- ( ) FIX EntityRefSerializer


## FIST - File storage

Idea: Storing and loading files in different locations / remote services

Dependencies: none

- ( ) common interfaces for a file storage, driver, bucket, file
- ( ) File system driver
- ( ) encryption for names and content
- ( ) signing of content to ensure data is consistent

## RECO - Request Container

Idea: A minimal DI container that lives for only on ktor-call

Dependencies: none

- ( ) implement a DI that lazily instantiates requested services
- ( ) consistency check for container (running in dev mode)
  - ( ) are all dependencies present for all services
  - ( ) detect cyclic dependencies

## CULO - Custom Locations Server

Idea: The locations service also handles the parameter conversion.
      The Parameter conversion needs to access the call, in order to 
      access the injected attributes. This is currently not possible

Dependencis: RECO

## INCO- Insight Collectors

Idea: Collect insights about code execution, e.g. db-queries, request-details, etc for display in DEBA

Dependencies: none

- ( ) common interfaces for insight collectors 

## DEBA - Debug Bar (inspired by symphony)

Dependencies: RECO, INCO, FIST

- ( ) gather information from all INCOs
- ( ) write collected information to file storage 
- ( ) UI - Bottom of screen bar
- ( ) UI - full screen with all details 

