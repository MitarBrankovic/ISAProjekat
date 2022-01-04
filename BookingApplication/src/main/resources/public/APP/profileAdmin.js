Vue.component("ProfileAdmin", {
    data: function() {
        return {
            activeUser:"",
            instructorUser: "",
            sendCheck:false,
            textAreaDelete: "",
        }
    },
    template :`
    <div>
    <div class="container-fluid my-container">
    <div class="row my-row  justify-content-around">
        <div class="col-sm-6 my-col">
            <span class="input-group-text" style="margin-top: 2%;">Ime:</span>
            <input type="text" id="name" disabled class="form-control" v-model="activeUser.name" placeholder="Unesite ime...">
            <span class="input-group-text" style="margin-top: 2%;">Prezime:</span>
            <input type="text" id="surname" disabled class="form-control" v-model="activeUser.surname" placeholder="Unesite prezime...">
            <span class="input-group-text" style="margin-top: 2%;">E-mail adresa:</span>
            <input type="text" id="email" disabled class="form-control" v-model="activeUser.email" placeholder="Unesite korisničko ime...">
            <span class="input-group-text" style="margin-top: 2%;">Lozinka:</span>
            <input type="password" id="password" disabled class="form-control" v-model="activeUser.password" placeholder="Unesite lozinku...">
            <span class="input-group-text" style="margin-top: 2%;">Adresa:</span>
            <input type="text" id="address" disabled  class="form-control" v-model="activeUser.address" placeholder="Unesite adresu (ulica, broj)...">
            <span class="input-group-text" style="margin-top: 2%;">Grad:</span>
            <input type="text" id="city" disabled class="form-control" v-model="activeUser.city" placeholder="Unesite grad...">
            <span class="input-group-text" style="margin-top: 2%;">Država:</span>
            <input type="text" id="country" disabled class="form-control" v-model="activeUser.country" placeholder="Unesite državu...">
            <span class="input-group-text" style="margin-top: 2%;">Broj telefona:</span>
            <input type="text" id="phone" disabled class="form-control" v-model="activeUser.phoneNumber" placeholder="Unesite broj telefona...">
		</div>
            <div class="col-sm-6 my-col">
              <img src="images/myinfo.png" height="450" width="600" style="margin-top: 5%;">
              <button type="button" style="margin-top: 15%; margin-left: 7%;" v-on:click="enableInfoEdit()" class="btn btn-secondary btn-lg">Promeni podatke</button>
              <button type="button" style="margin-top: 15%; margin-left: 7%;" v-on:click="saveInfoEdit()" class="btn btn-success btn-lg">Sačuvaj izmene</button>
            </div>
          </div>
    </div>
</div>
</div>	  
    	`
    	,
    methods: {
    	saveInfoEdit() {
    		if (this.checkInfoEdit()) {
        	axios
				.post('/admin/editAdmin', this.activeUser)	
				.then(response => {
	                	this.activeUser = response.data
			 			localStorage.setItem("activeUser", JSON.stringify(this.activeUser));
		        });	
		        this.disableInfoEdit()
		        Swal.fire({ icon: 'success', title: 'Uspešno ste sačuvali izmene !', showConfirmButton: false, timer: 1500 })
	        }
	        else {
	            Swal.fire({icon: 'error', title: 'Greška', text: 'Niste uneli sve potrebne podatke !'})
	        }
        },
        
        sendRequest(){
        	if (this.textAreaDelete == "") {
        		Swal.fire({icon: 'error', title: 'Greška', text: 'Popunite polje za razlog brisanja naloga !'})
        	} else {
        	if (!this.sendCheck) {
	            axios
	            .post('/appUser/sendRequest/' + this.textAreaDelete + "/" + this.activeUser.id)
	            .then(response=>{
	                //window.location.reload()
	                this.editClick = false
	                this.sendCheck = response.data
	                $('#deleteAccountReq').modal('hide');
	                this.textAreaDelete = ""
	            })
	            .catch(error=>{
	                console.log("Greska.")	
	                alert("Podaci su lose uneti.")
	                window.location.reload()
	            })
	        }
	        else {
	        	Swal.fire({icon: 'error', title: 'Greška', text: 'Već ste poslali zahtev za brisanje naloga !'})
	        }
	        }

        },
       
        checkInfoEdit(){
        	if (this.activeUser.name !== "" && this.activeUser.surname !== "" &&  this.activeUser.email !== "" && 
        	this.activeUser.password !== "" && this.activeUser.address !== "" && this.activeUser.city !== "" && 
        	this.activeUser.country !== "" && this.activeUser.phoneNumber !== "")
        		return true;
        	else
        		return false;
        },   
    	
    	enableInfoEdit() {
    			document.getElementById("name").disabled = false 
    			document.getElementById("surname").disabled = false 
    			document.getElementById("email").disabled = false 
    			document.getElementById("password").disabled = false 
    			document.getElementById("address").disabled = false 
    			document.getElementById("country").disabled = false 
    			document.getElementById("city").disabled = false 
    			document.getElementById("phone").disabled = false 
    	},
    	disableInfoEdit() {
    			document.getElementById("name").disabled = true 
    			document.getElementById("surname").disabled = true 
    			document.getElementById("email").disabled = true 
    			document.getElementById("password").disabled = true 
    			document.getElementById("address").disabled = true 
    			document.getElementById("country").disabled = true 
    			document.getElementById("city").disabled = true 
    			document.getElementById("phone").disabled = true 
    	},
    	
    },
    mounted(){
    	 this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
        if(this.activeUser.role != 'admin')
            this.$router.push('/')

        userId = this.activeUser.id 
        axios
        .get('/appUser/requestExists/' + userId)
        .then(response=>{
            //window.location.reload()
            this.sendCheck = response.data
        })
        .catch(error=>{
            console.log("Greska.")	
            alert("Podaci su lose uneti.")
            window.location.reload()

        })
    	console.log(this.activeUser)
    },

});