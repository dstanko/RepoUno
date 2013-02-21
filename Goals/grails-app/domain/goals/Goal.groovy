package goals

class Goal {
	String name
	String description
	static hasMany = [milestones: Milestone]
    static constraints = {
		name blank: false, nullable:false
		description()
    }
}
