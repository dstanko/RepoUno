package goals



import org.junit.*
import grails.test.mixin.*

@TestFor(GoalController)
@Mock(Goal)
class GoalControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/goal/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.goalInstanceList.size() == 0
        assert model.goalInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.goalInstance != null
    }

    void testSave() {
        controller.save()

        assert model.goalInstance != null
        assert view == '/goal/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/goal/show/1'
        assert controller.flash.message != null
        assert Goal.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/goal/list'

        populateValidParams(params)
        def goal = new Goal(params)

        assert goal.save() != null

        params.id = goal.id

        def model = controller.show()

        assert model.goalInstance == goal
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/goal/list'

        populateValidParams(params)
        def goal = new Goal(params)

        assert goal.save() != null

        params.id = goal.id

        def model = controller.edit()

        assert model.goalInstance == goal
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/goal/list'

        response.reset()

        populateValidParams(params)
        def goal = new Goal(params)

        assert goal.save() != null

        // test invalid parameters in update
        params.id = goal.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/goal/edit"
        assert model.goalInstance != null

        goal.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/goal/show/$goal.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        goal.clearErrors()

        populateValidParams(params)
        params.id = goal.id
        params.version = -1
        controller.update()

        assert view == "/goal/edit"
        assert model.goalInstance != null
        assert model.goalInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/goal/list'

        response.reset()

        populateValidParams(params)
        def goal = new Goal(params)

        assert goal.save() != null
        assert Goal.count() == 1

        params.id = goal.id

        controller.delete()

        assert Goal.count() == 0
        assert Goal.get(goal.id) == null
        assert response.redirectedUrl == '/goal/list'
    }
}
