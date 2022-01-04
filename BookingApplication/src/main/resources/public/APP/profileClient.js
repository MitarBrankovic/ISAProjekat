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
    <div>
        <h1 style="height: 10%;">Profile info</h1>
        <div>
            <form id="editForm" style="height: 100%; width: 100%;" method ="PUT" @submit.prevent = "submitForm">
			    <label class="col-sm-2 col-form-label" for="name"><b>Name</b></label>
			    <input id="name" pattern="[a-zA-Z]+[a-zA-Z ]+" title="Enter letters only." class="col-sm-2 col-form-control" type="text" v-model="activeUser.name" disabled required>
				<br>
                
				<label class="col-sm-2 col-form-label" for="surname"><b>Surname</b></label>
			    <input id="surname" pattern="[a-zA-Z]+[a-zA-Z ]+" title="Enter letters only." class="col-sm-2 col-form-control" type="text" v-model="activeUser.surname" disabled required>
				<br>
                
                
				<label class="col-sm-2 col-form-label" for="email"><b>Email</b></label>
			    <input id="email" class="col-sm-2 col-form-control" type="email" v-model="activeUser.email" disabled readonly>
				<br>
                
                <label class="col-sm-2 col-form-label" for="password"><b>Password</b></label>
			    <input id="password" class="col-sm-2 col-form-control" type="password" v-model="activeUser.password" @change='check_pass()' disabled required>
				<br>
                
			    <label class="col-sm-2 col-form-label" for="password-repeat"><b>Repeat password</b></label>
			    <input id="confirmPassword" class="col-sm-2 col-form-control" type="password" @change='check_pass()' disabled required>
			    <br>
                
				<label class="col-sm-2 col-form-label" for="address"><b>Address</b></label>
			    <input id="address" class="col-sm-2 col-form-control" type="text" v-model="activeUser.address" disabled required>
				<br>
                
                <label class="col-sm-2 col-form-label" for="city"><b>City</b></label>
			    <input id="city" pattern="[a-zA-Z]+[a-zA-Z ]+" title="Enter letters only." class="col-sm-2 col-form-control" type="text" v-model="activeUser.city" disabled required>
				<br>

                <label class="col-sm-2 col-form-label" for="country"><b>Country</b></label>
			    <input id="country" pattern="[a-zA-Z]+[a-zA-Z ]+" title="Enter letters only." class="col-sm-2 col-form-control" type="text" v-model="activeUser.country" disabled required>
				<br>

                <label class="col-sm-2 col-form-label" for="phoneNumber"><b>PhoneNumber</b></label>
			    <input id="phoneNumber" pattern="[0-9]+" title="Enter numbers only." class="col-sm-2 col-form-control" type="text" v-model="activeUser.phoneNumber" disabled required>
                <br>
                <button v-if="editClick==false" type="button" class="button" v-on:click="isEdit()">Edit</button>
                <button v-if="editClick==true" id="submit" type="submit" class="btn btn-success" disabled>Save</button>
                <button v-if="editClick==true" type="button" class="btn btn-secondary" v-on:click="cancelEdit()">Cancel</button>
            </form>
        </div>

        <div class="row" style="margin-top:10%">
            <div class= "col-md-4 container">
                <h4 v-if="sendCheck==false">Request for account deletion</h4>
                <button type="button" class="button" v-if="requestDelete==false && sendCheck==false" v-on:click="requestDelete=true">Open request</button> 
                <h4 v-if="sendCheck==true">Successfully sent request</h4>
                
                <div  v-if="requestDelete==true && sendCheck==false">
                    <textarea v-model="textAreaDelete" id="deleteArea" placeholder="Reason why you want to delete account" rows="4" cols="50"></textarea><br>
                    <button type="button" class="btn btn-primary" v-on:click="sendRequest()">Send request</button> 
                    <button type="button" class="btn btn-danger" v-on:click="requestDelete=false">Cancel</button> 
                
                </div>
            </div>
            <div class="col-md-2 razmak-card card">
                <h2><span class="bi bi-star"></span>Broj bodova</h2>
                <h3 style="text-align:center;">21</h3>
            </div>
            <div class="col-md-2 razmak-card card">
                <h2><span class="bi bi-person"></span>Status</h2>
                <h3 style="text-align:center;">Obican</h3>
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