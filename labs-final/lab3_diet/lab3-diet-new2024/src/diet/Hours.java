package diet;

public class Hours implements Comparable<Hours> {
    private boolean h;
	private int m;
	private int apertoChiuso;
	// = -1 ora in cui apre/ =1 ora in cui chiude / =0 è dentro orario apertura
	//0 potrebbe anche essere fuori orario ma se così fosse verrebbe bloccato dal check precedente sull'ora
	//anche per ora di chiusura il booleano deve essere settato a TRUE (uguale o maggiore/minore)
	//così guarderà i minuti rimanenti

	//controlla, in ordine da prima a dopo: h ; apertoChiuso ;  m

    //costruttore
    Hours(){
		this.h = false;
		this.apertoChiuso = 0;	
		this.m=0;
	}

	public void setMin(int min ){
		this.m = min;
	}

	public void setChiusura(){
		this.apertoChiuso = 1;
	}

	public void setApertura(){
		this.apertoChiuso = -1;
	}

	public void setHour(boolean value){
		this.h = value;
	}

	public boolean HourOpen(){
		return h;
	}

	public int getcheckIns(){
		return apertoChiuso;
	}

	@Override
	public int compareTo(Hours arg0) {
		// se il minuto sta dentro OK
		// se no torna un valore diverso
		if(arg0.m < this.m){
			return 1;
		}
		if(arg0.m > this.m) return -1;
		return 0;
	}

}
