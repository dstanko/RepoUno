<%@ page import="goals.Milestone" %>



<div class="fieldcontain ${hasErrors(bean: milestoneInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="milestone.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${milestoneInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: milestoneInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="milestone.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${milestoneInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: milestoneInstance, field: 'habits', 'error')} ">
	<label for="habits">
		<g:message code="milestone.habits.label" default="Habits" />
		
	</label>
	<g:select name="habits" from="${goals.Habit.list()}" multiple="multiple" optionKey="id" size="5" value="${milestoneInstance?.habits*.id}" class="many-to-many"/>
</div>

