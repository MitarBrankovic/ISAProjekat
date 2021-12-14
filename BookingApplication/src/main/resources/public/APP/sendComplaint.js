Vue.component("SendComplaint", {
    data: function() {
        return {
            activeUser:"",
            textAreaComplaint:"",
            cottages:[],
            oneCottage:""
        }
    },
    template : ` 
    <div>
        <div class="forma container">
            <form id="editForm" method ="POST" @submit.prevent = "submitForm">
                <h1>Send complaint</h1>
                <hr>
            
                <label class="col-sm-2 col-form-label" for="name"><b>Cottages</b></label>
                <select class="col-sm-2 col-form-select" v-model="oneCottage" requiered>
                    <option disabled value="">Please select one</option>
                    <option v-for="c in cottages" :value="c">{{c.name}}</option>
                </select><br><br>

			    <textarea class="col-sm-4 col-form-control" v-model="textAreaComplaint" id="deleteArea" placeholder="Write complaint text here..." rows="4" cols="50" required></textarea><br>
				<br>
                
                <hr>
                <button id="button1" type="submit" class="button">Send</button>
            </form>
        </div>
        

    </div>	  
`
    	,
    methods: {
        submitForm:function(){
            if(this.oneCottage){
                const complaintDto = {
                    text: this.textAreaComplaint,
                    cottage: this.oneCottage,
                    appUser: this.activeUser
                }
    
                axios
                .post('/client/sendComplaint', complaintDto)
                .then(response=>{
                    this.$router.push('/')
                    const Toast = Swal.mixin({
                        toast: true,
                        position: 'top-end',
                        showConfirmButton: false,
                        timer: 3000,
                        timerProgressBar: true,
                        didOpen: (toast) => {
                          toast.addEventListener('mouseenter', Swal.stopTimer)
                          toast.addEventListener('mouseleave', Swal.resumeTimer)
                        }
                      })
                      
                      Toast.fire({
                        icon: 'success',
                        title: 'Complaint sent successfully'
                      })
                })
                .catch(error=>{
                    console.log("Greska.")	
                    alert("Podaci su lose uneti.")
                    window.location.reload()
    
                })

            }else{
                Swal.fire({
                    icon: 'error',
                    title: 'Error...',
                    text: 'You need to select item from comboBox!',
                  })

            }



        }
    },
    mounted(){
        this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
        axios
        .get('/cottages/getAllCottages')
        .then(response=>{
            //window.location.reload()
            this.cottages = response.data
        })
        .catch(error=>{
            console.log("Greska.")	
            alert("Greska.")
            window.location.reload()

        })
    },

});