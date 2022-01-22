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
	<div class="container">    
        <div class="row">
        <div class="col-lg-3 col-md-2"></div>
        <div class="col-lg-6 col-md-8 login-box">
		<form id="registrationForm" method ="POST" @submit.prevent = "submitForm">
		  	<div class="container">
                <div class="col-lg-12 login-key">
                    <i class="bi bi-file-person" aria-hidden="true"></i>
                </div>
                <div class="col-lg-12 login-title">REGISTRACIJA</div><br>
			
			    <label class="col-sm-4 col-form-label" for="name"><b>Name</b></label>
			    <input name="nameInput" pattern="[a-zA-Z]+[a-zA-Z ]+" title="Enter letters only." class="col-sm-4 col-form-control" type="text" v-model="name" required>
				<br>
				
				<label class="col-sm-4 col-form-label" for="surname"><b>Surname</b></label>
			    <input pattern="[a-zA-Z]+[a-zA-Z ]+" title="Enter letters only." class="col-sm-4 col-form-control" type="text" v-model="surname" required>
				<br>
							
				<label class="col-sm-4 col-form-label" for="email"><b>Email</b></label>
			    <input class="col-sm-4 col-form-control" type="email" v-model="email" required>
				<br>

                <label class="col-sm-4 col-form-label" for="password"><b>Password</b></label>
			    <input id="password" class="col-sm-4 col-form-control" type="password" v-model="password" @change='check_pass()' required>
				<br>
				
			    <label class="col-sm-4 col-form-label" for="password-repeat"><b>Repeat password</b></label>
			    <input id="confirmPassword" class="col-sm-4 col-form-control" type="password" @change='check_pass()' required>
			    <br>

				<label class="col-sm-4 col-form-label" for="address"><b>Address</b></label>
			    <input class="col-sm-4 col-form-control" type="text" v-model="address" required>
				<br>

                <label class="col-sm-4 col-form-label" for="city"><b>City</b></label>
			    <input pattern="[a-zA-Z]+[a-zA-Z ]+" class="col-sm-4 col-form-control" type="text" v-model="city" required>
				<br>
                
                <label class="col-sm-4 col-form-label" for="country"><b>Country</b></label>
			    <input pattern="[a-zA-Z]+[a-zA-Z ]+" title="Enter letters only." class="col-sm-4 col-form-control" type="text" v-model="country" required>
				<br>

                <label class="col-sm-4 col-form-label" for="phoneNumber"><b>PhoneNumber</b></label>
			    <input pattern="[0-9]+" title="Enter numbers only." class="col-sm-4 col-form-control" type="text" v-model="phoneNumber" required>
				<br>

                <label class="col-sm-4 col-form-label" for="role"><b>Role</b></label>
				<select v-model="role" class="col-sm-4 col-form-select">
					<option value="client">Client</option>
				    <option value="cottage_owner">Cottage owner</option>
                    <option value="ship_owner">Ship owner</option>
                    <option value="fishing_instructor">Fishing instructor</option>
				</select>
				<br><br>
				
			    <button id="submit" class="button" type="submit" disabled>Register</button>
				<div class="container signin">
		    		<p>Vec imate nalog? <a href="#/login">Sign in</a>.</p>
		  		</div>
		  	</div>
		</form>
        </div></div></div>
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
			if (user.role == 'client') {
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
            else {
            	axios
	            .post('/registration/registerOwner',user)
	            .then(response=>{
	                this.bool = response.data
	                if(this.bool === true)
	                {
	                	Swal.fire({ icon: 'success', title: 'Vaš zahtev za registraciju je poslat adminstratoru. Kada bude odobren, bićete obavešteni putem mejla koji ste uneli.', showConfirmButton: false, timer: 2500 })
	                    this.$router.push('/')
	                }
	                else
	                {
	                    Swal.fire({ icon: 'error', title: 'Vaš zahtev za registraciju nije poslat, molimo Vas da proverite ispravnost podataka koje ste uneli.', showConfirmButton: false, timer: 2500 })
	                }
	
	            })
            }
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
        localStorage.removeItem('email')
    }

});