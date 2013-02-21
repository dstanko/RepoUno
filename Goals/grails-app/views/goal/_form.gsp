<%@ page import="goals.Goal" %>



<div class="fieldcontain ${hasErrors(bean: goalInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="goal.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${goalInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: goalInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="goal.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${goalInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: goalInstance, field: 'milestones', 'error')} ">
	<label for="milestones">
		<g:message code="goal.milestones.label" default="Milestones" />
		
	</label>
	<g:select name="milestones" from="${goals.Milestone.list()}" multiple="multiple" optionKey="id" size="5" value="${goalInstance?.milestones*.id}" class="many-to-many"/>
</div>

