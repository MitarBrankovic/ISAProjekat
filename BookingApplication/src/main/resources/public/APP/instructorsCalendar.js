Vue.component("InstructorsCalendar", {
    data: function() {
        return {
            activeUser:"",
            loyalty: "",
            splits: [{ label: 'John', class: 'john' }, { label: 'Kate', class: 'kate' }],
  			editable: { title: false, drag: false, resize: true, create: true, delete: true },
  			events: [],
  			selectedDate: new Date()
        }
	
    },
    template :`
    <div>
    <div class="flex mx-2" style="max-width: 800px">
    <vue-cal class="vuecal--green-theme" 
	    hide-weekends="hide-weekends" 
	    :selected-date="selectedDate" 
	    :time-from="8 * 60" 
	    :time-to="19 * 60" 
	    sticky-split-labels="sticky-split-labels" 
	    :editable-events="editable" 
	    active-view="week"
	    :events="events" 
	    today-button
	    @cell-focus="selectedDate = $event.date || $event"
	    style="height: 450px">
	</vue-cal>
</div>
	</div>	  
    	`
    	,
    methods: {
    	
    },
    
    components: { 'vue-cal': vuecal },
    
    computed: {
    // Get the Monday of the real time current week.
    previousFirstDayOfWeek () {
      return new Date(new Date().setDate(new Date().getDate() - (new Date().getDay() + 6) % 7))
    }
  },
    
    mounted () {
    // Place all the events in the real time current week.
    // Date.format() and Date.addDays() are helper methods added by Vue Cal.
    const monday = this.previousFirstDayOfWeek.format()
    const tuesday = this.previousFirstDayOfWeek.addDays(1).format()
    const thursday = this.previousFirstDayOfWeek.addDays(3).format()
    const friday = this.previousFirstDayOfWeek.addDays(4).format()
    const saturday = this.previousFirstDayOfWeek.addDays(5).format()
    const sunday = this.previousFirstDayOfWeek.addDays(6).format()
    this.events.push(
      {
        start: `${monday} 15:30`,
        end: `${monday} 17:30`,
        title: 'Tennis',
        resizable: false,
      },
      {
        start: `${tuesday} 15:30`,
        end: `${tuesday} 17:30`,
        title: 'Tennis',
        resizable: false,
      },
      {
        start: `${thursday} 09:00`,
        end: `${thursday} 11:30`,
        title: 'Golf',
        resizable: false,
      },
      {
        start: `${friday} 16:45`,
        end: `${friday} 18:45`,
        title: 'Movie',
        resizable: false,
      },
      {
        start: `${saturday} 16:45`,
        end: `${saturday} 18:45`,
        title: 'Movie',
        resizable: false,
      },
      {
        start: `${sunday} 16:45`,
        end: `${sunday} 18:45`,
        title: 'Movie',
        resizable: false,
      }
    )
      console.log(this.events)
      console.log(this.selectedDate)
  }

}
    
    
    
    
);



