package goals

import org.springframework.dao.DataIntegrityViolationException

class MilestoneController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [milestoneInstanceList: Milestone.list(params), milestoneInstanceTotal: Milestone.count()]
    }

    def create() {
        [milestoneInstance: new Milestone(params)]
    }

    def save() {
        def milestoneInstance = new Milestone(params)
        if (!milestoneInstance.save(flush: true)) {
            render(view: "create", model: [milestoneInstance: milestoneInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'milestone.label', default: 'Milestone'), milestoneInstance.id])
        redirect(action: "show", id: milestoneInstance.id)
    }

    def show(Long id) {
        def milestoneInstance = Milestone.get(id)
        if (!milestoneInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'milestone.label', default: 'Milestone'), id])
            redirect(action: "list")
            return
        }

        [milestoneInstance: milestoneInstance]
    }

    def edit(Long id) {
        def milestoneInstance = Milestone.get(id)
        if (!milestoneInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'milestone.label', default: 'Milestone'), id])
            redirect(action: "list")
            return
        }

        [milestoneInstance: milestoneInstance]
    }

    def update(Long id, Long version) {
        def milestoneInstance = Milestone.get(id)
        if (!milestoneInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'milestone.label', default: 'Milestone'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (milestoneInstance.version > version) {
                milestoneInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'milestone.label', default: 'Milestone')] as Object[],
                          "Another user has updated this Milestone while you were editing")
                render(view: "edit", model: [milestoneInstance: milestoneInstance])
                return
            }
        }

        milestoneInstance.properties = params

        if (!milestoneInstance.save(flush: true)) {
            render(view: "edit", model: [milestoneInstance: milestoneInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'milestone.label', default: 'Milestone'), milestoneInstance.id])
        redirect(action: "show", id: milestoneInstance.id)
    }

    def delete(Long id) {
        def milestoneInstance = Milestone.get(id)
        if (!milestoneInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'milestone.label', default: 'Milestone'), id])
            redirect(action: "list")
            return
        }

        try {
            milestoneInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'milestone.label', default: 'Milestone'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'milestone.label', default: 'Milestone'), id])
            redirect(action: "show", id: id)
        }
    }
}
