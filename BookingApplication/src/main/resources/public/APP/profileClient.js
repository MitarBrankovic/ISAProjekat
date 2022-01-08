Vue.component("ProfileClient", {
    data: function() {
        return {
            activeUser:"",
            helperUser:"",
            editClick:false,
            requestDelete:false,
            sendCheck:false,
            textAreaDelete:""
        }
    },
    template : ` 
    <div class="row">
        <div class="col-lg-3 col-md-2"></div>
			<div class="col-lg-6 col-md-8 login-box">
            <form id="editForm" style="height: 100%; width: 100%;" method ="PUT" @submit.prevent = "submitForm">
                <div class="forma container" style="margin-bottom:9px">
                    <div class="col-lg-12 login-key">
                        <i class="bi bi-info-circle" aria-hidden="true"></i>
                    </div>
                    <div class="col-lg-12 login-title">INFORMACIJE O PROFILU</div><br>

                    <label class="col-sm-4 col-form-label" for="name"><b>Ime</b></label>
                    <input id="name" pattern="[a-zA-Z]+[a-zA-Z ]+" title="Enter letters only." class="col-sm-4 col-form-control" type="text" v-model="activeUser.name" disabled required>
                    <br>
                    
                    <label class="col-sm-4 col-form-label" for="surname"><b>Prezime</b></label>
                    <input id="surname" pattern="[a-zA-Z]+[a-zA-Z ]+" title="Enter letters only." class="col-sm-4 col-form-control" type="text" v-model="activeUser.surname" disabled required>
                    <br>
                    
                    
                    <label class="col-sm-4 col-form-label" for="email"><b>Email</b></label>
                    <input id="email" class="col-sm-4 col-form-control" type="email" v-model="activeUser.email" disabled readonly>
                    <br>
                    
                    <label class="col-sm-4 col-form-label" for="password"><b>Sifra</b></label>
                    <input id="password" class="col-sm-4 col-form-control" type="password" v-model="activeUser.password" @change='check_pass()' disabled required>
                    <br>
                    
                    <label class="col-sm-4 col-form-label" for="password-repeat"><b>Ponovite sifru</b></label>
                    <input id="confirmPassword" class="col-sm-4 col-form-control" type="password" @change='check_pass()' disabled required>
                    <br>
                    
                    <label class="col-sm-4 col-form-label" for="address"><b>Adresa</b></label>
                    <input id="address" class="col-sm-4 col-form-control" type="text" v-model="activeUser.address" disabled required>
                    <br>
                    
                    <label class="col-sm-4 col-form-label" for="city"><b>Grad</b></label>
                    <input id="city" pattern="[a-zA-Z]+[a-zA-Z ]+" title="Enter letters only." class="col-sm-4 col-form-control" type="text" v-model="activeUser.city" disabled required>
                    <br>

                    <label class="col-sm-4 col-form-label" for="country"><b>Drzava</b></label>
                    <input id="country" pattern="[a-zA-Z]+[a-zA-Z ]+" title="Enter letters only." class="col-sm-4 col-form-control" type="text" v-model="activeUser.country" disabled required>
                    <br>

                    <label class="col-sm-4 col-form-label" for="phoneNumber"><b>Broj telefona</b></label>
                    <input id="phoneNumber" pattern="[0-9]+" title="Enter numbers only." class="col-sm-4 col-form-control" type="text" v-model="activeUser.phoneNumber" disabled required>
                    <br>
                    <button v-if="editClick==false" type="button" class="button" v-on:click="isEdit()">Izmeni</button>
                    <button v-if="editClick==true" id="submit" type="submit" class="btn btn-success" disabled>Sacuvaj</button>
                    <button v-if="editClick==true" type="button" class="btn btn-secondary" v-on:click="cancelEdit()">Otkazi</button>
                </div>
            </form>
        </div><br><br>

        <div class="row">
            <div class= "col-md-4 container">
                <h4 v-if="sendCheck==false">Zahtev za brisanje naloga</h4>
                <button type="button" class="button" v-if="requestDelete==false && sendCheck==false" v-on:click="requestDelete=true">Otvori zahtev</button> 
                <h4 v-if="sendCheck==true">Zahtev je uspesno poslat!</h4>
                
                <div  v-if="requestDelete==true && sendCheck==false">
                    <textarea v-model="textAreaDelete" id="deleteArea" placeholder="Razlog zbog kojeg zelite da obrisete nalog." rows="4" cols="50"></textarea><br>
                    <button type="button" class="btn btn-success" v-on:click="sendRequest()">Posalji zahtev</button> 
                    <button type="button" class="btn btn-secondary" v-on:click="requestDelete=false">Otkazi</button> 
                
                </div>
            </div>
            <div class="col-md-2 razmak-card card">
                <h2><span class="bi bi-star"></span>Broj bodova</h2>
                <h3 style="text-align:center;">{{activeUser.loyaltyPoints}}</h3>
            </div>
            <div class="col-md-2 razmak-card card">
                <h2><span class="bi bi-person"></span>Status</h2>
                <h3 style="text-align:center;" v-if="activeUser.loyaltyStatus == 'regular'">Osnovni</h3>
                <h3 style="text-align:center;" v-if="activeUser.loyaltyStatus == 'bronze'">Bronzani</h3>
                <h3 style="text-align:center;" v-if="activeUser.loyaltyStatus == 'silver'">Srebrni</h3>
                <h3 style="text-align:center;" v-if="activeUser.loyaltyStatus == 'gold'">Zlatni</h3>
            </div>
            <div class="col-md-2 razmak-card card">
                <h2><span class="bi bi-people"></span>Vrsta korisnika</h2>
                <h3 style="text-align:center;">Klijent</h3>
            </div>
        </div>

    </div>
    	`
    	,
    methods: {
        isEdit(){
            this.editClick = true
            document.getElementById("name").disabled = false 
            document.getElementById("surname").disabled = false 
            document.getElementById("email").disabled = false 
            document.getElementById("password").disabled = false 
            document.getElementById("confirmPassword").disabled = false
            document.getElementById("address").disabled = false 
            document.getElementById("city").disabled = false
            document.getElementById("country").disabled = false 
            document.getElementById("phoneNumber").disabled = false 

        },
        cancelEdit(){
            this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
            this.editClick = false
            document.getElementById("name").disabled = true 
            document.getElementById("surname").disabled = true 
            document.getElementById("email").disabled = true
            document.getElementById("password").disabled = true 
            document.getElementById("confirmPassword").disabled = true
            document.getElementById("address").disabled = true 
            document.getElementById("city").disabled = true
            document.getElementById("country").disabled = true 
            document.getElementById("phoneNumber").disabled = true 
        },
        submitForm:function(){
            axios
            .put('/client/edit',this.activeUser)
            .then(response=>{
                localStorage.setItem('activeUser',JSON.stringify(this.activeUser))
                window.location.reload()
                this.editClick = false
            })
            .catch(error=>{
                console.log("Greska.")	
                alert("Podaci su lose uneti.")
                window.location.reload()

            })
        },
        sendRequest:function(){
            userId = this.activeUser.id
            axios
            .post('/appUser/sendRequest/' + this.textAreaDelete + "/" + userId)
            .then(response=>{
                //window.location.reload()
                this.editClick = false
                this.sendCheck = response.data
            })
            .catch(error=>{
                console.log("Greska.")	
                alert("Podaci su lose uneti.")
                window.location.reload()

            })

        },
        check_pass(){
            if (document.getElementById('password').value ==
                    document.getElementById('confirmPassword').value) {
                document.getElementById('submit').disabled = false;
            } else {
                document.getElementById('submit').disabled = true;
            }
        }
    },
    mounted(){
        this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
        this.helperUser = JSON.parse(localStorage.getItem('activeUser'))
        if(this.activeUser.role != 'client')
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