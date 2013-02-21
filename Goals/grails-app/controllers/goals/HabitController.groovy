package goals

import org.springframework.dao.DataIntegrityViolationException

class HabitController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [habitInstanceList: Habit.list(params), habitInstanceTotal: Habit.count()]
    }

    def create() {
        [habitInstance: new Habit(params)]
    }

    def save() {
        def habitInstance = new Habit(params)
        if (!habitInstance.save(flush: true)) {
            render(view: "create", model: [habitInstance: habitInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'habit.label', default: 'Habit'), habitInstance.id])
        redirect(action: "show", id: habitInstance.id)
    }

    def show(Long id) {
        def habitInstance = Habit.get(id)
        if (!habitInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'habit.label', default: 'Habit'), id])
            redirect(action: "list")
            return
        }

        [habitInstance: habitInstance]
    }

    def edit(Long id) {
        def habitInstance = Habit.get(id)
        if (!habitInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'habit.label', default: 'Habit'), id])
            redirect(action: "list")
            return
        }

        [habitInstance: habitInstance]
    }

    def update(Long id, Long version) {
        def habitInstance = Habit.get(id)
        if (!habitInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'habit.label', default: 'Habit'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (habitInstance.version > version) {
                habitInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'habit.label', default: 'Habit')] as Object[],
                          "Another user has updated this Habit while you were editing")
                render(view: "edit", model: [habitInstance: habitInstance])
                return
            }
        }

        habitInstance.properties = params

        if (!habitInstance.save(flush: true)) {
            render(view: "edit", model: [habitInstance: habitInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'habit.label', default: 'Habit'), habitInstance.id])
        redirect(action: "show", id: habitInstance.id)
    }

    def delete(Long id) {
        def habitInstance = Habit.get(id)
        if (!habitInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'habit.label', default: 'Habit'), id])
            redirect(action: "list")
            return
        }

        try {
            habitInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'habit.label', default: 'Habit'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'habit.label', default: 'Habit'), id])
            redirect(action: "show", id: id)
        }
    }
}
