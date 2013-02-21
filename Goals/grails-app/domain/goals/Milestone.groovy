package goals

class Milestone {
	String name
	String description
	static hasMany = [habits: Habit]
	static constraints = {
		name blank: false, nullable:false
		description()
	}
}
