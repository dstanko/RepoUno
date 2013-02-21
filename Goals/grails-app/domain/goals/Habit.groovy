package goals

class Habit {
	String name
	String description
	static constraints = {
		name blank: false, nullable:false
		description()
	}
}
