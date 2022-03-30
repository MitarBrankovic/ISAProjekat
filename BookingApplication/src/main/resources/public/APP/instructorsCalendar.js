Vue.component("InstructorsCalendar", {
    data: function() {
        return {
            activeUser:"",
            loyalty: "",
        }
	
    },
    template :`
    <div>
    <div class="container-fluid my-container">
    <vue-cal style="height: 800px" />
    </div>
	</div>	  
    	`
    	,
    methods: {
    	
    },
    mounted(){
	
    },
    components: { 'vue-cal': vuecal }
});

