Vue.component("ProfileCottageOwner", {
    data: function() {
        return {
            activeUser:"",
        }
    },
    template :`
    <div>
    <div class="container-fluid my-container">
    <div class="row my-row  justify-content-around">
        <div class="col-sm-6 my-col">
            <span class="input-group-text" id="basic-addon1" style="margin-top: 2%;">Ime:</span>
            <input type="text" id="nameInput" class="form-control" v-model="activeUser.name" placeholder="Unesite ime...">
            <span class="input-group-text" id="basic-addon1" style="margin-top: 2%;">Prezime:</span>
            <input type="text" id="lastnameInput" class="form-control" v-model="activeUser.surname" placeholder="Unesite prezime...">
            <span class="input-group-text" id="basic-addon1" style="margin-top: 2%;">E-mail adresa:</span>
            <input type="text" id="usernameInput" class="form-control" v-model="activeUser.email" placeholder="Unesite korisničko ime...">
            <span class="input-group-text" id="basic-addon1" style="margin-top: 2%;">Lozinka:</span>
            <input type="password" id="passwordInput"  class="form-control" placeholder="Unesite lozinku...">
            <span class="input-group-text" id="basic-addon1" style="margin-top: 2%;">Adresa:</span>
            <input type="text" id="addressInput"   class="form-control" v-model="activeUser.address" placeholder="Unesite adresu (ulica, broj)...">
            <span class="input-group-text" id="basic-addon1" style="margin-top: 2%;">Grad:</span>
            <input type="text" id="birthDateInput" class="form-control" v-model="activeUser.city" placeholder="Unesite grad...">
            <span class="input-group-text" id="basic-addon1" style="margin-top: 2%;">Država:</span>
            <input type="text" id="birthDateInput" class="form-control" v-model="activeUser.country" placeholder="Unesite državu...">
            <span class="input-group-text" id="basic-addon1" style="margin-top: 2%;">Broj telefona:</span>
            <input type="text" id="birthDateInput"  class="form-control" v-model="activeUser.phoneNumber" placeholder="Unesite broj telefona...">
        </div>
        <div class="col-sm-6 my-col">
            <div style="margin-top: 5%; margin-left: 15%;">
                <img src="../images/myinfo.png" height="450" width="600">
            </div>
            <button type="button" style="margin-top: 5%; margin-left: 7%;" class="btn btn-secondary btn-lg">Promeni podatke</button>
            <button type="button" style="margin-top: 5%; margin-left: 7%;" class="btn btn-primary btn-lg">Sačuvaj izmene</button>
            <button type="button" style="margin-top: 5%; margin-left: 7%;" class="btn btn-danger btn-lg">Zahtev za brisanje naloga</button>
        </div>
    </div>
</div>
</div>
</div>	  
    	`
    	,
    methods: {
    
    },
    mounted(){
	this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
        if(this.activeUser.role != 'cottage_owner')
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
    },

});