Vue.component("Register", {
    data: function() {
        return {
			name:"",
			surname:"",
			email:"",
			password:"",
			address:"",
			city:"",
			country:"",
            phoneNumber:"",
            role:"",
            verificationCode:null,
            verified:false
        }
    },
    template:`  
	<div>    
		<form id="registrationForm" method ="POST" @submit.prevent = "submitForm">
		  	<div class="container">
			    <h1>Register</h1>
			    <hr>
			
			    <label class="col-sm-2 col-form-label" for="name"><b>Name</b></label>
			    <input class="col-sm-2 col-form-control" type="text" v-model="name" required>
				<br>
				
				<label class="col-sm-2 col-form-label" for="surname"><b>Surname</b></label>
			    <input class="col-sm-2 col-form-control" type="text" v-model="surname" required>
				<br>
				
				
				<label class="col-sm-2 col-form-label" for="email"><b>Email</b></label>
			    <input class="col-sm-2 col-form-control" type="text" v-model="email" required>
				<br>


                <label class="col-sm-2 col-form-label" for="password"><b>Password</b></label>
			    <input class="col-sm-2 col-form-control" type="password" v-model="password" required>
				<br>
				
			    <label class="col-sm-2 col-form-label" for="password-repeat"><b>Repeat password</b></label>
			    <input class="col-sm-2 col-form-control" type="password" name="password-repeat" id="password-repeat" required>
			    <hr>

				
				<label class="col-sm-2 col-form-label" for="address"><b>Address</b></label>
			    <input class="col-sm-2 col-form-control" type="text" v-model="address" required>
				<br>

                <label class="col-sm-2 col-form-label" for="city"><b>City</b></label>
			    <input class="col-sm-2 col-form-control" type="text" v-model="city" required>
				<br>
                
                <label class="col-sm-2 col-form-label" for="country"><b>Country</b></label>
			    <input class="col-sm-2 col-form-control" type="text" v-model="country" required>
				<br>

                <label class="col-sm-2 col-form-label" for="phoneNumber"><b>PhoneNumber</b></label>
			    <input class="col-sm-2 col-form-control" type="text" v-model="phoneNumber" required>
				<br>


                <label class="col-sm-2 col-form-label" for="role"><b>Role</b></label>
				<select v-model="role" class="col-sm-2 col-form-select">
					<option value="client">Client</option>
				    <option value="cottage_owner">Cottage owner</option>
                    <option value="ship_owner">Ship owner</option>
                    <option value="fishing_instructor">Fishing instructor</option>
				</select>
				<br>
				

			
			    <button class="button" type="submit">Register</button>
				<div class="container signin">
		    		<p>Already have an account? <a href="#">Sign in</a>.</p>
		  		</div>
		  	</div>
		</form>
	</div>
    `       
        ,
	 methods:{
        submitForm:function(){
            const user = {
                name:this.name,
                surname:this.surname,
                email: this.email,
                password: this.password,
                address:this.address,
                city:this.city,
				country:this.country,
                phoneNumber:this.phoneNumber,
                role:this.role,
				verificationCode:this.verificationCode,
                verified:this.verified
            }

            axios
            .post('/registration/registerUser',user)
            .then(response=>{
                localStorage.setItem('email', user.email)
                this.bool = response.data
                if(this.bool === true)
                {
                    this.$router.push('/emailVerification')
                }
                else
                {
                    Swal.fire('Any fool can use a computer')
                }

            })
        }
    },
    mounted(){
        localStorage.removeItem('email')
    }

});