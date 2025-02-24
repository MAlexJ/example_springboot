set a default category when the request does not provide one

ou can achieve this by modifying your MapStruct mapper to set a default category when the request does not provide one. Use @Mapping with defaultValue or a custom mapping method.

Solution: Using defaultValue in MapStruct
Modify your mapper:

@Mapper(componentModel = "spring")
public interface TaskMapper {

@Mapping(target = "id", ignore = true)
@Mapping(target = "created", ignore = true)
@Mapping(target = "updated", ignore = true)
@Mapping(target = "recurring", source = "isRecurring")
@Mapping(
target = "rewardAmount",
source = "rewardAmount",
qualifiedByName = "bigDecimalToDecimal128")
@Mapping(target = "category", source = "category", defaultValue = "STACK_GAME")
TaskEntity requestToEntity(TasksRequest request);
}
How It Works?
The defaultValue = "STACK_GAME" will automatically use STACK_GAME if the request has null in the category field.
However, this works only if category is a String in TasksRequest.
If category is an enum in TasksRequest,