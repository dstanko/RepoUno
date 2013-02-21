<%@ page import="goals.Habit" %>



<div class="fieldcontain ${hasErrors(bean: habitInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="habit.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${habitInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: habitInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="habit.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${habitInstance?.description}"/>
</div>

