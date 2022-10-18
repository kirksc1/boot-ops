# Validate Command
The 'validate' command is the core process by which the item manifest
is validated, typically as Git branch valdiation pre-merge.  Boot Ops executes this 
process through publishing of events to signal the start of each stage.
New behavior can be added at each of these stages by implementing
concrete applications of the 
[ApplicationListener<ApplicationEvent>][application-listener] interface.

## Events
| Event | Description | Typical Usage | Status |
|---|---|---|---|
| SystemInitiatedEvent | Occurs on initial start of the process | Validate the infrastructure is ready to be updated | Pending |
| ItemInitializationInitiatedEvent | Deserialization of manifest into an Item is complete | Population of defaults or other opinions | Supported |
| ItemInitializationCompletedEvent | Item and associated data is fully populated | Validation of Item data for structure and policy | Supported |
| ItemValidationInitiatedEvent | Item and associated data has been validated | Validation error processing, tracking of failure metrics and diagnostics | Supported |
| ItemValidationCompletedEvent | Item processing has completed | Tracking of metrics related to Items | Supported |
| SystemCompletedEvent | Occurs at the completion of all Item processing | Tracking of metrics related to the entire process | Pending |

[application-listener]: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/ApplicationListener.html