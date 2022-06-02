Vue.component("AddAdmin", {
    data: function() {
        return {
            activeUser:"",
            newAdmin: {name: "", surname: "", email: "", password: "", address: "", city: "", country: "", phoneNumber: ""},
        }
    },
    template :`
<div>
  <div class="container-fluid my-container">
    <div class="row my-row  justify-content-around">
        <div class="col-sm-6 my-col">
            <span class="input-group-text" style="margin-top: 2%;">Ime:</span>
            <input type="text" id="name" class="form-control" v-model="newAdmin.name" placeholder="Unesite ime...">
            <span class="input-group-text" style="margin-top: 2%;">Prezime:</span>
            <input type="text" id="surname" class="form-control" v-model="newAdmin.surname" placeholder="Unesite prezime...">
            <span class="input-group-text" style="margin-top: 2%;">E-mail adresa:</span>
            <input type="text" id="email" class="form-control" v-model="newAdmin.email" placeholder="Unesite korisničko ime...">
            <span class="input-group-text" style="margin-top: 2%;">Lozinka:</span>
            <input type="password" id="password" class="form-control" v-model="newAdmin.password" placeholder="Unesite lozinku...">
            <span class="input-group-text" style="margin-top: 2%;">Adresa:</span>
            <input type="text" id="address"  class="form-control" v-model="newAdmin.address" placeholder="Unesite adresu (ulica, broj)...">
            <span class="input-group-text" style="margin-top: 2%;">Grad:</span>
            <input type="text" id="city" class="form-control" v-model="newAdmin.city" placeholder="Unesite grad...">
            <span class="input-group-text" style="margin-top: 2%;">Država:</span>
            <input type="text" id="country" class="form-control" v-model="newAdmin.country" placeholder="Unesite državu...">
            <span class="input-group-text" style="margin-top: 2%;">Broj telefona:</span>
            <input type="text" id="phone" class="form-control" v-model="newAdmin.phoneNumber" placeholder="Unesite broj telefona...">
		</div>
            <div class="col-sm-6 my-col">
              <img src="images/myinfo.png" height="450" width="600" style="margin-top: 5%;">
              <button type="button" style="margin-top: 15%; margin-left: 7%;" v-on:click="addAdmin()" class="btn btn-success btn-lg">Dodaj administratora</button>
            </div>
          </div>
    </div>
</div> 
    	`
    	,
    methods: {
    	addAdmin() {
    		if (this.checkNewAdmin()) {
    			if (this.validateEmail()){
	        	axios
					.post('/admin/addAdmin', this.newAdmin,{
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})	
					.then(response => {
						if (response.data) {
		                	Swal.fire({ icon: 'success', title: 'Uspešno ste dodali novog administratora !', showConfirmButton: false, timer: 1500 })
			        		this.clearInputs();
			        	}
			        	else
			        		Swal.fire({icon: 'error', title: 'Greška', text: 'Već postoji korisnik sa unetom e-mail adresom !'})
			        });
			    }
			    else {
			    	Swal.fire({icon: 'error', title: 'Greška', text: 'Niste uneli ispravnu e-mail adresu !'})	
	        	}
	        }
	        else {
	            Swal.fire({icon: 'error', title: 'Greška', text: 'Niste uneli sve potrebne podatke !'})
        	}
        },
        
        checkNewAdmin(){
        	if (this.newAdmin.name !== "" && this.newAdmin.surname !== "" &&  this.newAdmin.email !== "" && 
        	this.newAdmin.password !== "" && this.newAdmin.address !== "" && this.newAdmin.city !== "" && 
        	this.newAdmin.country !== "" && this.newAdmin.phoneNumber !== "")
        		return true;
        	else
        		return false;
        },   
        
        clearInputs() {
        	this.newAdmin.name = "";
        	this.newAdmin.surname = "";
        	this.newAdmin.address = "";
        	this.newAdmin.email = "";
        	this.newAdmin.password = "";
        	this.newAdmin.country = "";
        	this.newAdmin.city = "";
        	this.newAdmin.phoneNumber = "";
        },
        
        validateEmail() {
		 if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(this.newAdmin.email)) {
		    return (true)
		 }
		 return (false)    	
		}
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