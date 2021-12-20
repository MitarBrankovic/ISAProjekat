Vue.component("ProfileClient", {
    data: function() {
        return {
            activeUser:"",
            editClick:false,
            requestDelete:false,
            sendCheck:false,
            textAreaDelete:""
        }
    },
    template : ` 
    <div>
        <div v-if="editClick==true" class="forma container">
            <form id="editForm" method ="PUT" @submit.prevent = "submitForm">
                <h1>Edit profile</h1>
                <hr>
            
			    <label class="col-sm-2 col-form-label" for="name"><b>Name</b></label>
			    <input class="col-sm-2 col-form-control" type="text" v-model="activeUser.name" required>
				<br>
                
				<label class="col-sm-2 col-form-label" for="surname"><b>Surname</b></label>
			    <input class="col-sm-2 col-form-control" type="text" v-model="activeUser.surname" required>
				<br>
                
                
				<label class="col-sm-2 col-form-label" for="email"><b>Email</b></label>
			    <input class="col-sm-2 col-form-control" type="email" v-model="activeUser.email" readonly>
				<br>
                
                <label class="col-sm-2 col-form-label" for="password"><b>Password</b></label>
			    <input id="password" class="col-sm-2 col-form-control" type="password" v-model="activeUser.password" @change='check_pass()' required>
				<br>
                
			    <label class="col-sm-2 col-form-label" for="password-repeat"><b>Repeat password</b></label>
			    <input id="confirmPassword" class="col-sm-2 col-form-control" type="password" @change='check_pass()' required>
			    <hr>
                
				<label class="col-sm-2 col-form-label" for="address"><b>Address</b></label>
			    <input class="col-sm-2 col-form-control" type="text" v-model="activeUser.address" required>
				<br>
                
                <label class="col-sm-2 col-form-label" for="city"><b>City</b></label>
			    <input class="col-sm-2 col-form-control" type="text" v-model="activeUser.city" required>
				<br>

                <label class="col-sm-2 col-form-label" for="country"><b>Country</b></label>
			    <input class="col-sm-2 col-form-control" type="text" v-model="activeUser.country" required>
				<br>

                <label class="col-sm-2 col-form-label" for="phoneNumber"><b>PhoneNumber</b></label>
			    <input class="col-sm-2 col-form-control" type="text" v-model="activeUser.phoneNumber" required>

                
                <hr>
                <button id="submit" type="submit" class="button" disabled>Save</button>
                <button type="button" class="button" v-on:click="editClick=false">Cancel</button>
            </form>
        </div>
        <div v-else class="forma container">
            <h1>Profile info</h1>
            <hr>
        
            <label class="col-sm-2 col-form-label" for="name"><b>Name</b></label>
            <input class="col-sm-2 col-form-control" type="text" v-model="activeUser.name"  readonly>
            <br>
            
            <label class="col-sm-2 col-form-label" for="surname"><b>Surname</b></label>
            <input class="col-sm-2 col-form-control" type="text" v-model="activeUser.surname" readonly>
            <br>
            
            
            <label class="col-sm-2 col-form-label" for="email"><b>Email</b></label>
            <input class="col-sm-2 col-form-control" type="email" v-model="activeUser.email" readonly>
            <br>
            
            <label class="col-sm-2 col-form-label" for="password"><b>Password</b></label>
            <input class="col-sm-2 col-form-control" type="password" v-model="activeUser.password" readonly>
            <br>
            
            <label class="col-sm-2 col-form-label" for="password-repeat"><b>Repeat password</b></label>
            <input class="col-sm-2 col-form-control" type="password" v-model="activeUser.password" name="password-repeat" id="password-repeat" readonly>
            <br>
            
            <label class="col-sm-2 col-form-label" for="address"><b>Address</b></label>
            <input class="col-sm-2 col-form-control" type="text" v-model="activeUser.address" readonly>
            <br>
            
            <label class="col-sm-2 col-form-label" for="city"><b>City</b></label>
            <input class="col-sm-2 col-form-control" type="text" v-model="activeUser.city" readonly>
            <br>

            <label class="col-sm-2 col-form-label" for="country"><b>Country</b></label>
            <input class="col-sm-2 col-form-control" type="text" v-model="activeUser.country" readonly>
            <br>

            <label class="col-sm-2 col-form-label" for="phoneNumber"><b>PhoneNumber</b></label>
            <input class="col-sm-2 col-form-control" type="text" v-model="activeUser.phoneNumber" readonly>
            <br>
            
        
            <button type="button" class="button" v-on:click="editClick=true">Edit</button>
        </div><hr>

        <div style="margin-top: 4%; justify-content: center;">
            <h4 v-if="sendCheck==false">Request for account deletion</h4>
            <button type="button" class="button" v-if="requestDelete==false && sendCheck==false" v-on:click="requestDelete=true">Open request</button> 
            <h4 v-if="sendCheck==true">Successfully sent request</h4>
            
            <div  v-if="requestDelete==true && sendCheck==false">
                <textarea v-model="textAreaDelete" id="deleteArea" placeholder="Reason why you want to delete account" rows="4" cols="50"></textarea><br>
                <button type="button" class="btn btn-primary" v-on:click="sendRequest()">Send request</button> 
                <button type="button" class="btn btn-danger" v-on:click="requestDelete=false">Cancel</button> 
            
            </div>
        </div>

    </div>	  
    	`
    	,
    methods: {
        submitForm:function(){
            axios
            .put('/client/edit',this.activeUser)
            .then(response=>{
                localStorage.setItem('activeUser',JSON.stringify(this.activeUser))
                this.$router.push('/')
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