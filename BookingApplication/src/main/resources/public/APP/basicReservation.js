Vue.component("BasicReservation", {
    data: function() {
        return {
            activeUser:"",
            stepper: 0,
            barProcentage: 25,
            times:[8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20],
            time:0,
            datePick:"",
            radio:""

        }
    },
    template:`  
        <div style="margin-top: 30px;" class="container">

            <div class="progress">
                <div name="bar" class="progress-bar bg-success progress-bar-striped progress-bar-animated" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 25%">25%</div>
            </div>

            <div v-if="stepper == 0" style="margin-top: 50px;">
                <div class="form-check form-check-inline">
                    <input v-model="radio" class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="vikendice">
                    <label class="form-check-label" for="inlineRadio1">Vikendice</label>
                </div>
                <div class="form-check form-check-inline">
                    <input v-model="radio" class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio2" value="brodovi">
                    <label class="form-check-label" for="inlineRadio2">Brodovi</label>
                </div>
                <div class="form-check form-check-inline">
                    <input v-model="radio" class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio3" value="avanture">
                    <label class="form-check-label" for="inlineRadio3">Avanture</label>
                </div><br><br><br>


                <input id="datePickerId" v-model="datePick" type="date" name="trip-start" min="0" ></input>
                <select class="col-sm-2 col-form-select" v-model="time" requiered>
                    <option disabled value="">Please select one</option>
                    <option v-for="c in times" :value="c">{{c}}:00</option>
                </select><br><br>

                <div class="centerIt">
                    <button class="btn btn-success" type="button" v-on:click="firstNext()">Next</button>
                </div>
            </div>


            <div v-if="stepper == 1">
                nesto
                <div class="centerIt">
                    <button class="btn btn-success" type="button" v-on:click="stepperDown()">Nazad</button>
                    <button class="btn btn-success" type="button" v-on:click="stepperUp()">Next</button>
                </div>
            </div>
            <div v-if="stepper == 2">
                nesto
                <div class="centerIt">
                    <button class="btn btn-success" type="button" v-on:click="stepperDown()">Nazad</button>
                    <button class="btn btn-success" type="button" v-on:click="stepperUp()">Next</button>
                </div>
            </div>
            <div v-if="stepper == 3">
                nesto
                <div class="centerIt">
                    <button class="btn btn-success" type="button" v-on:click="stepperDown()">Nazad</button>
                    <button class="btn btn-success" type="button" >Finish</button>
                </div>
            </div>
          
        
        </div>
    `       
        ,
    mounted(){
        this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
        if(this.activeUser.role != 'client')
        this.$router.push('/')

        datePickerId.min = new Date().toISOString().slice(0, -14);
	},
    methods:{
        firstNext() {
            let datum = this.datePick+"T00:00:00.000"
            const dto = {
                datePick: datum,
                time: this.time,
            }

            axios
            .post('/fishingAppointments/getAllFreeAdventures', dto)
            .then(response=>{
                //window.location.reload()
            })
            .catch(error=>{
                console.log("Greska.")	
                alert("Podaci su lose uneti.")
            })

            if(this.radio == "vikendice"){

            }else if(this.radio == "brodovi"){


            }else if(this.radio == "avanture"){


            }



            var bars = $('.progress-bar');
            for (i = 0; i < bars.length; i++) {
              console.log(i);
              var progress = $(bars[i]).attr('aria-valuenow');
              this.barProcentage +=25;
              progress = this.barProcentage;
              $(bars[i]).width(progress + '%');
              $(bars[i]).text(progress + "%");
            }
            this.stepper++
        },



        stepperUp() {
            var bars = $('.progress-bar');
            for (i = 0; i < bars.length; i++) {
              console.log(i);
              var progress = $(bars[i]).attr('aria-valuenow');
              this.barProcentage +=25;
              progress = this.barProcentage;
              $(bars[i]).width(progress + '%');
              $(bars[i]).text(progress + "%");
            }
            this.stepper++
        },
        stepperDown() {
            var bars = $('.progress-bar');
            for (i = 0; i < bars.length; i++) {
                console.log(i);
                var progress = $(bars[i]).attr('aria-valuenow');
                this.barProcentage -=25;
                progress = this.barProcentage;
                $(bars[i]).width(progress + '%');
                $(bars[i]).text(progress + "%");
              }
            this.stepper--
        }
    }
});
