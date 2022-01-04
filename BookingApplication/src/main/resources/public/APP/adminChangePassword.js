Vue.component("AdminChangePassword", {
    data: function() {
        return {
            activeUser:"",
            userId: "",
            password: "",
            passwordRepeat: ""
        }
    },
    template :`
    <div>
		<div class="container-fluid">
			<h1 style="margin-top: 2%; color: #5cb85c;">Promena lozinke</h1>
			<hr>
			<label class="col-sm-2 col-form-label" for="email"><b>Nova lozinka</b></label>
			<input class="col-sm-2 col-form-control" id="password" type="password" v-model="password">
			<br>
			<label class="col-sm-2 col-form-label" for="password"><b>Ponovi lozinku</b></label>
			<input class="col-sm-2 col-form-control" id="repeat" type="password" v-model="passwordRepeat">
			<br><br><br>
			<button class="button" v-on:click="changePassword()">Promeni lozinku</button>
			<br>
		</div>
	</div>	  
    	`
    	,
    methods: {

        changePassword(){
        if (this.checkPassword()){
         	axios
               .put('/admin/changePassword/' + this.activeUser.id + "/" + this.password)
               .then(response=>{
               		Swal.fire({ icon: 'success', title: 'Uspešno ste promenili lozinku ! Sada se možete prijaviti.', showConfirmButton: false, timer: 2500 })
               		this.$router.push('/')
               })
               .catch(error=>{
                   console.log("Greska.")	
                   alert("Greska.")
                   window.location.reload()
               })
           }
        },
        
        checkPassword(){
        	if (this.password.trim() == "" || this.passwordRepeat.trim() == "") {
        		Swal.fire({icon: 'error', title: 'Greška', text: 'Morate uneti lozinku i ponoviti je !'})
        		return false
        	}
        	else if (this.password.trim() != this.passwordRepeat.trim()) {
        		Swal.fire({icon: 'error', title: 'Greška', text: 'Lozinke se ne poklapaju.Pokušajte ponovo.'})
        		return false	
        	}
        	else
        		return true; 
        },
    },
    mounted(){
        this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
        if(this.activeUser.role != 'admin'){
            this.$router.push('/')
        }
        if(this.activeUser.verified == true){
            this.$router.push('/')   
        } 
        localStorage.removeItem('activeUser');
    },
});