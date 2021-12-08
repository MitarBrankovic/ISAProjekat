Vue.component("ProfileClient", {
    data: function() {
        return {
            activeUser:"",
            editClick:false
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
			    <input class="col-sm-2 col-form-control" type="text" v-model="activeUser.email" readonly>
				<br>
                
                <label class="col-sm-2 col-form-label" for="password"><b>Password</b></label>
			    <input class="col-sm-2 col-form-control" type="password" v-model="activeUser.password" required>
				<br>
                
			    <label class="col-sm-2 col-form-label" for="password-repeat"><b>Repeat password</b></label>
			    <input class="col-sm-2 col-form-control" type="password" v-model="activeUser.password" name="password-repeat" id="password-repeat" required>
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
                <button type="submit" class="button">Save</button>
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
            <input class="col-sm-2 col-form-control" type="text" v-model="activeUser.email" readonly>
            <br>
            
            <label class="col-sm-2 col-form-label" for="password"><b>Password</b></label>
            <input class="col-sm-2 col-form-control" type="password" v-model="activeUser.password" readonly>
            <br>
            
            <label class="col-sm-2 col-form-label" for="password-repeat"><b>Repeat password</b></label>
            <input class="col-sm-2 col-form-control" type="password" v-model="activeUser.password" name="password-repeat" id="password-repeat" readonly>
            <hr>
            
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
        }
    },
    mounted(){
        this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
        if(this.activeUser.role != 'client')
            this.$router.push('/')
    },

});