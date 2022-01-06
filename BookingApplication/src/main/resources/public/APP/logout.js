Vue.component("Logout",{
	data: function(){
		return{
		}
	},
	
    template:` 
        <p>Logging out...</p>  
	`
		,
		methods : {
			
        },
        created(){
           
            localStorage.removeItem('activeUser')
            localStorage.removeItem('jwt')
            this.$router.push("/")
            window.location.reload()
        }
});