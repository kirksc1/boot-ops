# Converge Command
The 'converge' command is the core process by which the item manifest
is applied to the appropriate infrastructure.  Boot Ops executes this 
process through publishing of events to signal the start of each stage.
Converge behavior can be added at each of these stages by implementing
concrete applications of the 
[ApplicationListener<ApplicationEvent>][application-listener] interface.

## Events
| Event | Description | Typical Usage | Status |
|---|---|---|---|
| SystemInitiatedEvent | Occurs on initial start of the process | Validate the infrastructure is ready to be updated | Pending |
| ItemInitializationInitiatedEvent | Deserialization of manifest into an Item is complete | Population of defaults or other opinions | Supported |
| ItemInitializationCompletedEvent | Item and associated data is fully populated | TBD | Supported |
| ItemValidationInitiatedEvent | Item and associated data is ready for validation | Validation of Item data for structure and policy | Supported |
| ItemValidationCompletedEvent | Item and associated data has been validated | Validation error processing, tracking of failure metrics and diagnostics | Supported |
| ItemConvergeInitiatedEvent | Item and associated data is ready to be converged | Apply Item and associated data to infrastructure | Supported |
| ItemConvergeCompletedEvent | Item and associated data has been converged | Tracking of converge or change metrics | Supported |
| ItemCompletedEvent | Item processing has completed | Tracking of metrics related to Items | Supported |
| SystemCompletedEvent | Occurs at the completion of all Item processing | Tracking of metrics related to the entire process | Pending |

[application-listener]: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/ApplicationListener.html