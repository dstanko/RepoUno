package goals



import org.junit.*
import grails.test.mixin.*

@TestFor(HabitController)
@Mock(Habit)
class HabitControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/habit/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.habitInstanceList.size() == 0
        assert model.habitInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.habitInstance != null
    }

    void testSave() {
        controller.save()

        assert model.habitInstance != null
        assert view == '/habit/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/habit/show/1'
        assert controller.flash.message != null
        assert Habit.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/habit/list'

        populateValidParams(params)
        def habit = new Habit(params)

        assert habit.save() != null

        params.id = habit.id

        def model = controller.show()

        assert model.habitInstance == habit
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/habit/list'

        populateValidParams(params)
        def habit = new Habit(params)

        assert habit.save() != null

        params.id = habit.id

        def model = controller.edit()

        assert model.habitInstance == habit
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/habit/list'

        response.reset()

        populateValidParams(params)
        def habit = new Habit(params)

        assert habit.save() != null

        // test invalid parameters in update
        params.id = habit.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/habit/edit"
        assert model.habitInstance != null

        habit.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/habit/show/$habit.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        habit.clearErrors()

        populateValidParams(params)
        params.id = habit.id
        params.version = -1
        controller.update()

        assert view == "/habit/edit"
        assert model.habitInstance != null
        assert model.habitInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/habit/list'

        response.reset()

        populateValidParams(params)
        def habit = new Habit(params)

        assert habit.save() != null
        assert Habit.count() == 1

        params.id = habit.id

        controller.delete()

        assert Habit.count() == 0
        assert Habit.get(habit.id) == null
        assert response.redirectedUrl == '/habit/list'
    }
}
