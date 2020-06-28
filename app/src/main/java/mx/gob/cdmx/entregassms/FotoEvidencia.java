package mx.gob.cdmx.entregassms;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings.Secure;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class FotoEvidencia extends Activity {

    public static String getHostName(String defValue) {
        try {
            Method getString = Build.class.getDeclaredMethod("getString", String.class);
            getString.setAccessible(true);
            return getString.invoke(null, "net.hostname").toString();
        } catch (Exception ex) {
            return defValue;
        }
    }


    private String TAG = "FotoEvidencia";
    static String ID = getHostName(null);
    static String prefix = ID;

    Calendar c = Calendar.getInstance();

    SimpleDateFormat df1 = new SimpleDateFormat("yyyMMdd");
    String formattedDateFecha = df1.format(c.getTime());

    SimpleDateFormat df6 = new SimpleDateFormat("yyyMMdd");
    String formattedDate1 = df6.format(c.getTime());

    SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
    String formattedDate2 = df2.format(c.getTime());

    SimpleDateFormat df3 = new SimpleDateFormat("yyy-MM-dd");
    String formattedDate3 = df3.format(c.getTime());

    SimpleDateFormat df4 = new SimpleDateFormat("HH:mm:ss a");
    String formattedDate4 = df4.format(c.getTime());

    SimpleDateFormat df5 = new SimpleDateFormat("HH:mm:ss");
    String formattedDate5 = df5.format(c.getTime());

    UsuariosSQLiteHelper3 usdbh3;
    private SQLiteDatabase db3;

    private ImageButton camara;
    private ImageView imagen;
    private ImageView imagen2;
    private ImageView imagen3;
    private TextView Texto;
    //	private EditText nombreImagen;
    private String nombreImagen;
    private String nombreImagen2;
    private String nombreImagen3;
    private Button Guarda;
    private Uri output;
    private String foto;
    private String foto2;
    private String foto3;
    private File file;
    private File file2;
    private File file3;

    private String video;
    private String nombreVideo;

    private String la_foto;
    private String nombreF;
    private String nombreD;

    private File file4;
    private File file5;
    private File file6;
    List<String> list;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    private Spinner spinnerPUBLICO;
    String alcaldia, laCasilla, distF, distL;

    UsuariosSQLiteHelper usdbh;
    private Spinner spinner;
    private Spinner spinner2;
    UsuariosSQLiteHelper Udb;
    private SQLiteDatabase db;
    double latitude;
    double longitude;
    String laLatitud;
    String laLongitud;
    public String tiempo;
    String date = formattedDate1.toString();
    public String maximo = "";
    int elMaximo;
    String laSeccion;

    public EditText comentario;
    public EditText editNombreFoto;

    // NORMAL
    Nombre nom = new Nombre();
    String nombreE = nom.nombreEncuesta();

    String upLoadServerUriFotos = "https://opinion.cdmx.gob.mx/cgi-bin/php/recibeFotos" + nombreE + ".php?encuesta=" + nombreE + "";
    int serverResponseCode = 0;

    private static final int READ_BLOCK_SIZE = 100000;

    public String tablet;

//    public String sacaChip(){
//		String deviceId = Secure.getString(this.getContentResolver(),Secure.ANDROID_ID);
//		tablet=deviceId;	
//		return tablet;
//	}
//    public String sacaChip(){
//		prefix = ID.substring(8);
//		return prefix;
//	}
//    public String sacaChip(){
//    	String deviceId = Secure.getString(this.getContentResolver(),Secure.ANDROID_ID);
//        TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        String Imei = tManager.getDeviceId();
//    	return Imei;
//    	}

    public String cachaNumFoto() {
        Bundle datos = this.getIntent().getExtras();
        String num_foto = datos.getString("num_foto");
        return num_foto;
    }

    public String cachaFolio() {
        Bundle datos = this.getIntent().getExtras();
        String folio = datos.getString("folio");
        return folio;
    }

    public String sacaChip() {
        String deviceId = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);
        tablet = deviceId;
        return tablet;
    }

    public String cachaNombre() {
        Bundle datos = this.getIntent().getExtras();
        String Nombre = datos.getString("Nombre");
        return Nombre;
    }

    public String cachaCuantos() {
        Bundle datos = this.getIntent().getExtras();
        String cuantos = datos.getString("cuantos");
        return cuantos;
    }

    public String cachaLatitud() {
        Bundle datos = this.getIntent().getExtras();
        String latitud = datos.getString("latitud");
        return latitud;
    }

    public String cachaLongitud() {
        Bundle datos = this.getIntent().getExtras();
        String longitud = datos.getString("longitud");
        return longitud;
    }

    public String cachaNumeracion() {
        Bundle datos = this.getIntent().getExtras();
        String numeracion = datos.getString("numeracion");
        return numeracion;
    }

    public String cachaSeccionGPS() {
        Bundle datos = this.getIntent().getExtras();
        String seccion_gps = datos.getString("seccion_gps");
        return seccion_gps;
    }

    public String cachaSeccion() {
        Bundle datos = this.getIntent().getExtras();
        String seccion = datos.getString("seccion");
        return seccion;
    }


    public Integer cachaConsecutivoDiario() {
        Bundle datos = this.getIntent().getExtras();
        Integer consecutivo_diario = datos.getInt("consecutivo_diario");
        return consecutivo_diario;
    }


    public String cachaDiario() {

        elMaximo = Integer.parseInt(sacaMaximo().toString());

        String diario = String.valueOf(elMaximo);

        if (diario.length() == 1) {
            diario = "00" + diario;
        } else if (diario.length() == 2) {
            diario = "0" + diario;
        } else {
        }
        return diario;
    }

    public String cachaUsuario() {
        Bundle datos = this.getIntent().getExtras();
        String usuario = datos.getString("usuario");
        return usuario;
    }

    public String cachaDireccion() {
        Bundle datos = this.getIntent().getExtras();
        String direccion = datos.getString("Direccion");
        return direccion;
    }

    public String cachaNombreE() {
        Bundle datos = this.getIntent().getExtras();
        String nombre_encuesta = datos.getString("nombre_encuesta");
        return nombre_encuesta;
    }

    public String cachaTablet() {
        Bundle datos = this.getIntent().getExtras();
        String tablet = datos.getString("tablet");
        return tablet;
    }


    public String cachaMax() {
        Bundle datos = this.getIntent().getExtras();
        String max = datos.getString("max");
        return max;
    }

    public String cachaId() {
        Bundle datos = this.getIntent().getExtras();
        String id = datos.getString("id");
        return id;
    }

    public String cachaIdNuevo() {
        Bundle datos = this.getIntent().getExtras();
        String id_nuevo = datos.getString("id_nuevo");
        return id_nuevo;
    }

    public String cachaDelegacion() {
        Bundle datos = this.getIntent().getExtras();
        String delegacion = datos.getString("delegacion");
        return delegacion;
    }

    public String cachaCargo() {
        //   Bundle datos=this.getIntent().getExtras();
        String cargo = "Presidente";
        return cargo;
    }

    public String cachaDescripcion() {
        //Bundle datos=this.getIntent().getExtras();
        String descripcion = "Descripcion";
        return descripcion;
    }

    public String cachaQuien() {
        //Bundle datos=this.getIntent().getExtras();
        String quien = "Quien";
        return quien;
    }

    public String nombreArchivo() {
        String date = formattedDate1.toString();
        String var2 = ".txt";
        String var3 = date + var2;
        final String nombre = date + "_" + cachaTablet() + "_PRESIDENTE";
        return nombre;
    }

    public long t1() {
        Bundle datos = this.getIntent().getExtras();
        long t1 = datos.getLong("t1");
        return t1;
    }

    public String sacaImei() {
        TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            String szImei = TelephonyMgr.getDeviceId(); // Requires READ_PHONE_STATE
            System.out.println("Mi Número: " + szImei);

            return szImei;
        }
        String szImei = TelephonyMgr.getDeviceId(); // Requires READ_PHONE_STATE
		System.out.println("Mi Número: " + szImei);

		return szImei;
	}
    
    public String elTiempo(){
        //Para la diferenci entre tiempos
        Calendar t2 = Calendar.getInstance();
        long milis2 = t2.getTimeInMillis();
        long diff = milis2 - t1();


        //System.out.println("CALCULOS 2");
        // calcular la diferencia en minutos
        long diffSegundos = diff / 1000;

        long diffMinutos = diffSegundos/60;

        long residuo=diffSegundos%60;

        System.out.println(diffSegundos);
        System.out.println(diffMinutos);
        System.out.println(residuo);

//	    			DecimalFormat df = new DecimalFormat("#.##");
//	    			DecimalFormat df1 = new DecimalFormat("#");

        tiempo=diffMinutos+":"+residuo;

        return tiempo;

    }
    
    

	public void actualizaEncuesta(){


		
		String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreE + "_"
				+ sacaImei() + "";
		usdbh = new UsuariosSQLiteHelper(this, "F", null, 1, DATABASE_NAME);

		db = usdbh.getWritableDatabase();
		
		

	        String id= String.valueOf(sacaConsecutivo());

        GPSTracker gps = new GPSTracker(this);
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

        String strLatitud = String.valueOf(latitude);
        String strLongitud = String.valueOf(longitude);


        if (latitude == 0.0) {
            latitude = Double.valueOf(sacaLatitud());

        }

        if (longitude == 0.0) {
            longitude = Double.valueOf(sacaLongitud());

        }

        String strLatitud2 = String.valueOf(latitude);
        String strLongitud2 = String.valueOf(longitude);


        String seccion=cachaSeccion();

        elMaximo = Integer.parseInt(sacaMaximo().toString());

        String diario= String.valueOf(elMaximo);
        String elTipo= "Entrega";

	
		 String laFoto=cachaCuantos();
//         nombreD = diario+"_"+sacaImei()+"_"+date+"_"+laFoto;

        int num_foto=Integer.parseInt(cachaNumFoto())+1;

        String cuantas_fotos=String.valueOf(num_foto);

        nombreD = date+"_"+diario+"_"+seccion+"_"+cachaNombre()+"_"+sacaImei()+"_"+cachaFolio()+"_"+strLatitud2+"_"+strLongitud2;
        String Nombre_f=nombreD;

        guardaFoto(cuantas_fotos,Nombre_f,elTipo);
		
			
			if (db != null) {
				db.execSQL("update encuestas set "
				+ "fotos='"+cuantas_fotos+"' "
				+ " where consecutivo_diario='" + diario + "' and id='"+id+"'");
			
				 Log.i("QUERY: ","update encuestas set "
				+ "fotos='"+cuantas_fotos+"' "
				+ " where consecutivo_diario='" + diario + "' and id='"+id+"'");
			}
			
				
					dialogo();
				
					new UpdateFotos().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			
			
			
	
	}
	
	
	public void dialogo() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("¿Tomar otra foto?").setTitle("IMPORTANTE").setCancelable(false)
				.setNegativeButton("SALIR", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

//						detenerGrabacion();

						Intent i = new Intent(FotoEvidencia.this, Entrada.class);
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(i);
						System.exit(0); // metodo que se debe implementar
					}
				}).setPositiveButton("CONTINUAR", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

//						detenerGrabacion();

                        int num_foto=Integer.parseInt(cachaNumFoto())+1;

                        String cuantas_fotos=String.valueOf(num_foto);

						Intent i = new Intent(FotoEvidencia.this, FotoEvidencia.class);
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						i.putExtra("Nombre", cachaNombre());
                        i.putExtra("consecutivo_diario", cachaConsecutivoDiario());
                        i.putExtra("seccion", cachaSeccion());
                        i.putExtra("folio", cachaFolio());
                        i.putExtra("num_foto",cuantas_fotos);
						startActivity(i);
						System.exit(0); // metodo que se debe implementar
					}
				});
		AlertDialog alert = builder.create();
		alert.show();

	}
	
	
    ///////    METODO PARA VERIFICAR LA CONEXIï¿½N A INTERNET
    public static boolean verificaConexion(Context ctx) {
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // No sï¿½lo wifi, tambiï¿½n GPRS
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        // este bucle deberï¿½a no ser tan ï¿½apa
        for (int i = 0; i < 2; i++) {
            // ï¿½Tenemos conexiï¿½n? ponemos a true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                bConectado = true;
            }
        }
        return bConectado;
    }

    
    //EVENTO AL PULSAR EL BOTON ATRAS
  	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
// Esto es lo que hace mi botón al pulsar ir a atrás
            Toast.makeText(getApplicationContext(), "No puedes regresar \n\nToma la Foto",
                    Toast.LENGTH_SHORT).show();
        	
//        	dialogoAbandono();
        	
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.foto_evidencia);

        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(this,this));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        
        try{
        	
//        	 //para desactivar el wifi
//            WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE); 
////            wifiManager.setWifiEnabled(true);
//            wifiManager.setWifiEnabled(false);
        	
        	
        	Nombre nom = new Nombre();
			String nombreE = nom.nombreEncuesta();

			GPSTracker gps = new GPSTracker(this);
			latitude = gps.getLatitude();
			longitude = gps.getLongitude();

			String strLatitud = String.valueOf(latitude);
			String strLongitud = String.valueOf(longitude);
			
			
			if (latitude == 0.0) {
				latitude = Double.valueOf(sacaLatitud());
				
			}

			if (longitude == 0.0) {
				longitude = Double.valueOf(sacaLongitud());
				
			}

			String strLatitud2 = String.valueOf(latitude);
			String strLongitud2 = String.valueOf(longitude);
        	
        	 
             String seccion=cachaSeccion();
        
        	 elMaximo = Integer.parseInt(sacaMaximo().toString());

             String diario= String.valueOf(elMaximo);
        
             nombreD = date+"_"+diario+"_"+seccion+"_"+cachaNombre()+"_"+sacaImei()+"_"+cachaFolio()+"_"+strLatitud2+"_"+strLongitud2;
 
            
       
        
        Log.i("Foto", "El Diario : "+diario);
        
        Log.i("Foto", "Cuantas fotos: "+cachaCuantos());
        
        Log.i("Foto", "Consecutivo diario: "+diario);
        
        Log.i("Foto", "NombreD: "+nombreD);
        
            
       
        
        imagen=(ImageView)findViewById(R.id.imagen);
        Texto=(TextView)findViewById(R.id.textViewVivienda);
        
        Texto.setText("Foto Evidencia");

        la_foto = Environment.getExternalStorageDirectory() +"/Fotos/FotosEntregaSMS"+formattedDateFecha+"N"+"/"+nombreD.trim();
        File fileF=new File(la_foto);

        if(fileF.exists()){
            Bitmap thumb = BitmapFactory.decodeFile(la_foto);
           // thumb = Bitmap.createScaledBitmap(thumb, 600, 400, false);
            imagen.setImageBitmap(thumb);

        }else{
//			imagen2.setImageResource(R.drawable.morena_logo);
        }




        camara=(ImageButton)findViewById(R.id.camara);

        camara.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {

                elMaximo = Integer.parseInt(sacaMaximo().toString());

                String diario= String.valueOf(elMaximo);

                if(diario.length()==1){
                    diario="00"+diario;
                }else if(diario.length()==2){
                    diario="0"+diario;
                }else{
                }
                
                


//                String seccion = cachaSeccion();
//                
//                nombreD = diario+"_"+seccion+"_"+cachaNombre()+"_"+sacaImei()+"_"+date;
                
				nombreImagen = nombreD;

     

                    if (!nombreImagen.trim().equalsIgnoreCase("")) {
                        getCamara();
                    } else
                        Toast.makeText(FotoEvidencia.this, "Debe nombrar el archivo primero", Toast.LENGTH_LONG)
                                .show();
                	}
            

        });



        Guarda=(Button)findViewById(R.id.guardar);

        //PARA VER SI ESTA O NO ESTï¿½ CONECTADO


//            Toast.makeText(getBaseContext(),"Sin Conexion...!", Toast.LENGTH_LONG).show();
            Guarda.setOnClickListener(new OnClickListener(){


                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                   if(imagen.getDrawable() != null){
                        Log.i("datos f", "Solo hay foto");
                       
                        actualizaEncuesta();
                     
                        
                    }else {
                        Toast.makeText(FotoEvidencia.this, "Debe Tomar Una Foto", Toast.LENGTH_LONG).show();
                        Log.i("datos f", "No hay Nada");
                    }
                }
            });
            
        } catch (Exception e) {
			String stackTrace = Log.getStackTraceString(e);
	          Log.i("encuestas","Error todo OnCreate"+ stackTrace);
		}//////FIN CATCH ONCREATE/////////////////////////////
        }
    
    class cambia extends AsyncTask<String, Void, String> {

		ProgressDialog dialog;

		protected void onPreExecute() {
			super.onPreExecute();
//			dialog = new ProgressDialog(FotoDocumento.this);
//			dialog.setMessage("Enviando");
//			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//			dialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			
			//guardarArchivo();
			return null;
		}

		protected void onPostExecute(String result) {
			super.onPostExecute(result);
//			dialog.dismiss();

		}

	}

    public void guardaFoto(String num_foto,String nombre_f,String tipo_foto) {

        FileOutputStream fout = null;
        try {
            // INSERTA EN LA BASE DE DATOS //

            final String F = "File dbfile";

            Nombre nom = new Nombre();
            String nombreE = nom.nombreEncuesta();

            String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreE + "_"+ sacaImei() + "";

            // Abrimos la base de datos 'DBUsuarios' en modo escritura
            usdbh = new UsuariosSQLiteHelper(this, "F", null, 1, DATABASE_NAME);

            db = usdbh.getWritableDatabase();

            // NORMAL

            GPSTracker gps = new GPSTracker(this);
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            String strLatitud = String.valueOf(latitude);
            String strLongitud = String.valueOf(longitude);

            if (latitude == 0.0) {
                if (sacaLatitud() == null) {
                    latitude = 0.0;
                } else {
                    latitude = Double.valueOf(sacaLatitud());
                }

            }

            if (longitude == 0.0) {
                if (sacaLongitud() == null) {
                    longitude = 0.0;
                } else {
                    longitude = Double.valueOf(sacaLongitud());
                }

            }

            long consecutivoObtenido = 0;
            ContentValues values = new ContentValues();

            if (db != null) {

                values.put("consecutivo_diario", cachaConsecutivoDiario());
                values.put("usuario", cachaNombre().toUpperCase());
                values.put("nombre_programa", nombreE.toUpperCase());
                values.put("fecha", formattedDate1);
                values.put("hora", formattedDate5);
                values.put("imei", sacaImei() == null ? "0" : sacaImei());
                values.put("latitud", strLatitud);
                values.put("longitud", strLongitud);

                values.put("folio", cachaFolio());
                values.put("numero_foto", num_foto);
                values.put("nombre_foto", nombre_f);
                values.put("tipo", tipo_foto);

                db.insert("fotos", null, values);
            }

//            values.put("consecutivo", consecutivoObtenido);


            //db.close();

            System.out.println("Latitud  " + strLatitud);
            System.out.println("Longitud  " + strLongitud);

            // FIN INSERTA BASE DE DATOS //

        } catch (Exception e) {
            String stackTrace = Log.getStackTraceString(e);
            Log.i(TAG, "================>> Inserta registros" + stackTrace);

        }

    }
    
   
    public void dialogoCierre(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("¿Desea continuar Encuestando..?")
    	        .setTitle("IMPORTANTE")
    	        .setCancelable(false)
    	        .setNegativeButton("SALIR",
    	                new DialogInterface.OnClickListener() {
    	                    public void onClick(DialogInterface dialog, int id) {
    	                    	
    	                    	
    	                    	Intent i =new Intent(FotoEvidencia.this,Entrada.class);
    	        				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	        				startActivity(i);
    	        				System.exit(0); // metodo que se debe implementar
    	                    }
    	                })
    	        .setPositiveButton("CONTINUAR",
    	                new DialogInterface.OnClickListener() {
    	                    public void onClick(DialogInterface dialog, int id) {
    	                    	
    	        				Intent i =new Intent(FotoEvidencia.this,MainActivity.class);
    	        				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	        				i.putExtra("Nombre", cachaNombre());
    	        				i.putExtra("max","0");
    	        				startActivity(i);
    	        				System.exit(0); // metodo que se debe implementar
    	                    }
    	                });
    	AlertDialog alert = builder.create();
    	alert.show();
    	
    }
    

    private void getCamara(){
    	
    	elMaximo = Integer.parseInt(sacaMaximo().toString());
        int numero = 0;
        
        if(cachaNumeracion()==null){
        	numero=numero+1;
        }else{
        	numero=Integer.valueOf(cachaNumeracion()); 
        }
        
        

        String diario= String.valueOf(elMaximo);
        String diario_control= String.valueOf(elMaximo);

        if(diario.length()==1){
            diario="00"+diario;
        }else if(diario.length()==2){
            diario="0"+diario;
        }else {
            diario=diario;
        }
        
            foto = Environment.getExternalStorageDirectory() +"/Fotos/FotosEntregaSMS"+formattedDate1+"N"+"/"+nombreD.trim()+".jpg";

        
            Uri output = Uri.fromFile(new File(foto));

            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
    		
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
            startActivityForResult(intent, 0);
            
            }
            
        
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        	if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
                setPic();
            }
        }
        
        private void setPic() {
            // Get the dimensions of the View
//            int targetW = imagen.getWidth()/4;
//            int targetH = imagen.getHeight()/4;
            
            int targetW = imagen.getWidth();
            int targetH = imagen.getHeight();
         
            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(foto, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;
         
            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
         
            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;
         
            Bitmap bitmap = BitmapFactory.decodeFile(foto, bmOptions);
            imagen.setImageBitmap(bitmap);
        }

        private String sacaMaximo() {

    		Set<String> set = new HashSet<String>();

    		final String F = "File dbfile";

    		// Abrimos la base de datos 'DBUsuarios' en modo escritura
    		String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreE + "_"
    				+ sacaImei() + "";
    		usdbh = new UsuariosSQLiteHelper(this, "F", null, 1, DATABASE_NAME);

    		db = usdbh.getReadableDatabase();

    		String selectQuery = "SELECT count(*) FROM encuestas where fecha='" + formattedDate3 + "'";
    		Log.i("Foto", "==========> Query sacaMaximo: "+ selectQuery);

    		Cursor cursor = db.rawQuery(selectQuery, null);

    		if (cursor.moveToFirst()) {
    			do {

    				maximo = cursor.getString(0);

    			} while (cursor.moveToNext());
    		}

    		cursor.close();
//    		db.close();

    		return maximo;
    	} 
        
        private String sacaConsecutivo() {

    		Set<String> set = new HashSet<String>();

    		final String F = "File dbfile";

    		// Abrimos la base de datos 'DBUsuarios' en modo escritura
    		String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreE + "_"
    				+ sacaImei() + "";
    		usdbh = new UsuariosSQLiteHelper(this, "F", null, 1, DATABASE_NAME);

    		db = usdbh.getReadableDatabase();

    		String selectQuery = "SELECT count(*) FROM encuestas order by id desc limit 1";

    		Cursor cursor = db.rawQuery(selectQuery, null);

    		if (cursor.moveToFirst()) {
    			do {

    				maximo = cursor.getString(0);

    			} while (cursor.moveToNext());
    		}

    		cursor.close();
//    		db.close();

    		return maximo;
    	} 

        
        //////////   UPLOAD DE FOTOS ////////
        
		//Enviar Fotos
		

		public int uploadFotos(String sourceFileUri,String fech) {

		File sdCard;
		sdCard = Environment.getExternalStorageDirectory();
		//final String pathFotos = sdCard.getAbsolutePath() + "/"+ nombreEncuesta+"-Audio"+fech;
		final String pathFotos = sdCard.getAbsolutePath() +"/Fotos/FotosEntregaSMS"+formattedDateFecha+"N";

		String fileName = sourceFileUri;

		HttpURLConnection conn = null;
		DataOutputStream dos = null;  
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024; 
		File sourceFile = new File(sourceFileUri); 
		 
		if (!sourceFile.isFile()) {
		     
//		     dialog.dismiss(); 
		     Log.i(TAG, " =====> archivo:  El Archivo no existe... :" + pathFotos + "" + "/" + "20161124_002_359083065132816_1.jpg");
		     runOnUiThread(new Runnable() {
		         public void run() {

		         }
		     }); 
		      
		     return 0;
		  
		}
		else
		{
		     try { 
		           // open a URL connection to the Servlet
		         FileInputStream fileInputStream = new FileInputStream(sourceFile);
		         URL url = new URL(upLoadServerUriFotos);
		         // Open a HTTP  connection to  the URL
		         conn = (HttpURLConnection) url.openConnection(); 
		         conn.setDoInput(true); // Allow Inputs
		         conn.setDoOutput(true); // Allow Outputs
		         conn.setUseCaches(false); // Don't use a Cached Copy
		         conn.setRequestMethod("POST");
		         conn.setRequestProperty("Connection", "Keep-Alive");
		         conn.setRequestProperty("ENCTYPE", "multipart/form-data");
		         conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
		         conn.setRequestProperty("uploaded_file", fileName); 
		          
		         dos = new DataOutputStream(conn.getOutputStream());

		         dos.writeBytes(twoHyphens + boundary + lineEnd); 
		         dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + fileName + "\""
							+ lineEnd);
		          
		         dos.writeBytes(lineEnd);

		         // create a buffer of  maximum size
		         bytesAvailable = fileInputStream.available(); 

		         bufferSize = Math.min(bytesAvailable, maxBufferSize);
		         buffer = new byte[bufferSize];
		         // read file and write it into form...
		         bytesRead = fileInputStream.read(buffer, 0, bufferSize);  
		         while (bytesRead > 0) {
		           dos.write(buffer, 0, bufferSize);
		           bytesAvailable = fileInputStream.available();
		           bufferSize = Math.min(bytesAvailable, maxBufferSize);
		           bytesRead = fileInputStream.read(buffer, 0, bufferSize);   
		          }
		         // send multipart form data necesssary after file data...
		         dos.writeBytes(lineEnd);
		         dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
		         // Responses from the server (code and message)
		         serverResponseCode = conn.getResponseCode();
		         String serverResponseMessage = conn.getResponseMessage();

		         Log.i("Foto", "HTTP Response fotos is : "+ serverResponseMessage + ": " + serverResponseCode);
		          
		         if(serverResponseCode == 200){
		              
		             runOnUiThread(new Runnable() {
		                  public void run() {
		                       
		                      String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
		                                    +" http://www.androidexample.com/media/uploads/"
		                                    +"20161124_002_359083065132816_1.jpg";

//		                      Toast.makeText(Entrada.this, "File Upload Complete."+msg,Toast.LENGTH_SHORT).show();
		                  }
		              });                
		         }    
		          
		         //close the streams //
		         fileInputStream.close();
		         dos.flush();
		         dos.close();
		           
		    } catch (MalformedURLException ex) {
		         
//		        dialog.dismiss();  
		        ex.printStackTrace();
		         
		        runOnUiThread(new Runnable() {
		            public void run() {
//		                messageText.setText("MalformedURLException Exception : check script url.");
//		                Toast.makeText(CalendarViewFotos.this, "MalformedURLException", 
//		                                                    Toast.LENGTH_SHORT).show();
		            }
		        });
		         
		        Log.i(TAG, " =====> archivo:  El Archivo no existe... :" + "Upload file to server "+ "error: " + ex.getMessage());

//		        Log.e("Upload file to server", "error: " + ex.getMessage(), ex);  
		    } catch (Exception e) {
		         
//		        dialog.dismiss();  
		        e.printStackTrace();
		         
		        runOnUiThread(new Runnable() {
		            public void run() {
//		                messageText.setText("Error de Internet");
//		                Toast.makeText(CalendarViewFotos.this, "Error de Internet", 
//		                        Toast.LENGTH_SHORT).show();
		            }
		        });
		        Log.i(TAG, " =====> archivo:  El Archivo no existe... :" + "Upload file to server Exception "+ "Exception : "+ e.getMessage());

//		        Log.e("Upload file to server Exception", "Exception : "
//		                                         + e.getMessage(), e);  
		    }
		    return serverResponseCode; 
		     
		 } // End else block 
		} 

		class UpdateFotos extends AsyncTask<String, Float, String> {

			protected void onPreExecute() {
				super.onPreExecute();
				Log.i("Foto", "CarlosQ:  Entra ====>");
			}
			
			
			@Override
			protected String doInBackground(String... params) {

				
				final String date2 = formattedDateFecha;
				 File sdCard;
					sdCard = Environment.getExternalStorageDirectory();
					final String pathFotos = sdCard.getAbsolutePath() +"/Fotos/FotosEntregaSMS"+date2+"N/";
					final String pathFotosN = sdCard.getAbsolutePath() +"/Fotos/FotosEntregaSMS"+date2+"N/";
					final String pathFotosF = sdCard.getAbsolutePath() +"/Fotos/FotosEntregaSMS"+date2+ "/";;
		        
			        String sDirectorio = pathFotos;
					final File f = new File(sDirectorio);
					Log.i("Foto","CarlosQ =======> path de fotos: "+pathFotos);
					final String URL_fotos = "https://opinion.cdmx.gob.mx/fotografias/entrega_sms/";

				Log.i("Foto", "CarlosQ =======> lista de fotos: " + pathFotos);
				Log.i("Foto", "CarlosQ =======> pathFotosN: " + pathFotosN);
				Log.i("Foto", "CarlosQ =======> pathFotosF: " + pathFotosF);
				Log.i("Foto", "CarlosQ =======> URL: " + URL_fotos);

				File F = new File(pathFotos);

				try {
					
					if (F.exists()) {
						
					File[] ficheros = F.listFiles();
					
					for (int i = 0; i <ficheros.length; i++) {
	                    //Simulamos cierto retraso
	                    try {Thread.sleep(500); }
	                    catch (InterruptedException e) {}
	 
	                    publishProgress(i/(float)(ficheros.length)); //Actualizamos los valores
	                }
	 

					 String seccion=cachaSeccion();
				        
		        	 elMaximo = Integer.parseInt(sacaMaximo().toString());

		             String diario= String.valueOf(elMaximo);
		             
		             
		             Nombre nom = new Nombre();
		 			String nombreE = nom.nombreEncuesta();

		 			GPSTracker gps = new GPSTracker(FotoEvidencia.this);
		 			latitude = gps.getLatitude();
		 			longitude = gps.getLongitude();

		 			String strLatitud = String.valueOf(latitude);
		 			String strLongitud = String.valueOf(longitude);
		 			
		 			
		 			if (latitude == 0.0) {
		 				latitude = Double.valueOf(sacaLatitud());
		 				
		 			}

		 			if (longitude == 0.0) {
		 				longitude = Double.valueOf(sacaLongitud());
		 				
		 			}

		 			String strLatitud2 = String.valueOf(latitude);
		 			String strLongitud2 = String.valueOf(longitude);


                        nombreD = date+"_"+diario+"_"+seccion+"_"+cachaNombre()+"_"+sacaImei()+"_"+cachaFolio()+"_"+strLatitud2+"_"+strLongitud2;
		  
						String[] s = new String[ficheros.length];
						String[] t = new String[ficheros.length];
						for (int x = 0; x < ficheros.length; x++) {
							Log.i("Foto", "CarlosQ ===========> lista: " + ficheros[x].getName());
							s[x] = pathFotos + "/" + ficheros[x].getName();
							t[x] = ficheros[x].getName();
							// solo la foto
//							t[x] = nombreD.trim()+".jpg";
							
						//	uploadFotos(s[x],date2);
							 URL u = new URL (URL_fotos+t[x]);
     						HttpURLConnection huc =  ( HttpURLConnection )  u.openConnection (); 
     						huc.setRequestMethod ("GET");  //OR  huc.setRequestMethod ("HEAD"); 
     						huc.connect () ; 
     						huc.getResponseCode();
     						Log.i("Foto", "CarlosQ: =====================> Respuesta del servidor " + huc.getResponseCode());
     						if(huc.getResponseCode()==200){
     							moveFile(pathFotosN, t[x], pathFotosF);
     							Log.i("Foto", "CarlosQ: ================>  Foto en el servidor y Movida a otra carpeta ====>" + t[x] );
     						}else if(huc.getResponseCode()==404){
     							uploadFotos(s[x],date2);
     							Log.i("Foto", "CarlosQ: =================> Foto Enviada y aún sin Moverse ====>" + t[x] );
     						}
						}
						// first parameter is d files second parameter is zip file name
						
					} else {
						Log.i(null, "lista 2: " + "No existe el directorio");
					}
					// first parameter is d files second parameter is zip file name

				} catch (Exception e) {
					// TODO Auto-generated catch block
					Log.i(null, "error zip: " + "_" + e.getMessage());
				}

				

				return date2;
			}
			
			
			protected void onProgressUpdate (Float... valores) {
	              int p = Math.round(100*valores[0]);

	          }



			protected void onPostExecute(String date2) {
				super.onPostExecute(date2);

		}

		}

		private void moveFile(String inputPath, String inputFile, String outputPath) {

InputStream in = null;
OutputStream out = null;
try {

    //create output directory if it doesn't exist
    File dir = new File (outputPath); 
    if (!dir.exists())
    {
        dir.mkdirs();
    }


    in = new FileInputStream(inputPath + inputFile);        
    out = new FileOutputStream(outputPath + inputFile);

    byte[] buffer = new byte[1024];
    int read;
    while ((read = in.read(buffer)) != -1) {
        out.write(buffer, 0, read);
    }
    in.close();
    in = null;

        // write the output file
        out.flush();
    out.close();
    out = null;

    // delete the original file
    new File(inputPath + inputFile).delete();  


} 

     catch (FileNotFoundException fnfe1) {
    Log.i(null,"Archivos  tag"+ fnfe1.getMessage());
}
      catch (Exception e) {
    	  Log.i(null,"Archivos  tag"+ e.getMessage());
}

}

		
		private String sacaLatitud() {
			Set<String> set = new HashSet<String>();
			String acceso = null;
			final String F = "File dbfile";
			// Abrimos la base de datos 'DBUsuarios' en modo escritura
			usdbh3 = new UsuariosSQLiteHelper3(this);
			db3 = usdbh3.getReadableDatabase();
			String selectQuery = "select latitud from ubicacion order by id desc limit 1";
			Cursor cursor = db3.rawQuery(selectQuery, null);
			if (cursor.moveToFirst()) {
				do {
					acceso = cursor.getString(0);
				} while (cursor.moveToNext());
			}
			cursor.close();
			// db.close();

			return acceso;
		}

		private String sacaLongitud() {
			Set<String> set = new HashSet<String>();
			String acceso = null;
			final String F = "File dbfile";
			// Abrimos la base de datos 'DBUsuarios' en modo escritura
			usdbh3 = new UsuariosSQLiteHelper3(this);
			db3 = usdbh3.getReadableDatabase();
			String selectQuery = "select longitud from ubicacion order by id desc limit 1";
			Cursor cursor = db3.rawQuery(selectQuery, null);
			if (cursor.moveToFirst()) {
				do {
					acceso = cursor.getString(0);
				} while (cursor.moveToNext());
			}
			cursor.close();
			// db.close();

			return acceso;
		}

	


}



