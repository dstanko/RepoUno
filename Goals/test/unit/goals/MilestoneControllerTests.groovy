package goals



import org.junit.*
import grails.test.mixin.*

@TestFor(MilestoneController)
@Mock(Milestone)
class MilestoneControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/milestone/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.milestoneInstanceList.size() == 0
        assert model.milestoneInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.milestoneInstance != null
    }

    void testSave() {
        controller.save()

        assert model.milestoneInstance != null
        assert view == '/milestone/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/milestone/show/1'
        assert controller.flash.message != null
        assert Milestone.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/milestone/list'

        populateValidParams(params)
        def milestone = new Milestone(params)

        assert milestone.save() != null

        params.id = milestone.id

        def model = controller.show()

        assert model.milestoneInstance == milestone
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/milestone/list'

        populateValidParams(params)
        def milestone = new Milestone(params)

        assert milestone.save() != null

        params.id = milestone.id

        def model = controller.edit()

        assert model.milestoneInstance == milestone
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/milestone/list'

        response.reset()

        populateValidParams(params)
        def milestone = new Milestone(params)

        assert milestone.save() != null

        // test invalid parameters in update
        params.id = milestone.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/milestone/edit"
        assert model.milestoneInstance != null

        milestone.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/milestone/show/$milestone.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        milestone.clearErrors()

        populateValidParams(params)
        params.id = milestone.id
        params.version = -1
        controller.update()

        assert view == "/milestone/edit"
        assert model.milestoneInstance != null
        assert model.milestoneInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/milestone/list'

        response.reset()

        populateValidParams(params)
        def milestone = new Milestone(params)

        assert milestone.save() != null
        assert Milestone.count() == 1

        params.id = milestone.id

        controller.delete()

        assert Milestone.count() == 0
        assert Milestone.get(milestone.id) == null
        assert response.redirectedUrl == '/milestone/list'
    }
}
