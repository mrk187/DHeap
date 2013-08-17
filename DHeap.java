package alda.heap;


public class DHeap<AnyType extends Comparable<? super AnyType>>
{
	private int d;     //The D variable
	private int currentSize;      // Number of elements in heap
	private AnyType [ ] array; // The heap array
	private static final int DEFAULT_CAPACITY = 10;

	/*
	 En vanlig heap har en binŠr konstruktion, varje nod har tvŒ barnnoder i 
	 en sŒdan datastruktur. Elementen Šr sorterade frŒn vŠnster till hšger och 
	 varje nod Šr mindre Šn sina barn i en stigande ordning. 
	 Elementen ligger fšrvisso i en Array fast det hŒller en trŠd konstruktion. 
	 Detta innebŠr att fšr varje nod vi fšr in mŒste vi sortera sŒ att 
	 fšrhŒllandet mellan fšrŠldernoden samt fšrstabarnnoden kan bestŠmmas 
	 genom att dela eller multiplicera med tvŒ. Det minsta elementet finns 
	 alltsŒ alltid i rooten, index 1 i arrayen.
	 
	 Fšr att skapa en D-Heap dŠr man kan ange hur mŒnga barn, varje nod fŒr ha
	 fŒr man ta in i berŠkningen att gšra utrymme till barnen. Man fŒr alltsŒ
	 fšra in variabel d (d=antalBarn) vid infšring av ny data, vid radering och sjŠlvklart vid sortering. 
	 Man vill alltsŒ skapa utrymme fšr d antal barnnoderna. Nedan fšljer en
	 rapport om vilka Šndringa man fŒr gšra pŒ en binŠrheap fšr att det ska
	 bli en D-Heap.
	 
	 HŠr fŒr man Šndra frŒn DEFAULT_CAPACITY som tidigare angett en default
	 av tio element i trŠdet till att istŠllet vara en default av minst ett binŠrt heap.
	 Kapacitet kan man fortfarande sŠtta i en annan konstruktor, men dŒ
	 trŠdet har mšjlighet att vŠxa och minska Šr kapacitet kanske inte 
	 det viktiga lŠngre, dŒ vi gšr en D-Heap. Vi gšr Šven ett anrop till nŠsta
	 konstruktor som tar emot argument fšr antal d (barn) per "fšrŠlder"nod genom this() anropet.
	
	 
	 * WAS::::
	 
	 public BinaryHeap(){
	 this(DEFAULT_CAPACITY);
	 }
	 
	 * */
	public DHeap( )
	{
		this(2);
	}
	/* 
	 I denna konstruktor tar vi emot ett argument fšr antal d i heapen. 
	 HŠr skapar vi en konstruktor fšr att man ska kunna ange hur mŒnga 
	 barnnoder en fšrŠldernod ska ha. Detta Šr variabeln d fšr antal barn. 
	 Denna konstruktor anropas alltsŒ varje gŒng dŒ vi Šven gšr ett anrop till 
	 den om anvŠndaren inte anger ett vŠrde fšr d vid deklarering. 
	 HŠr fŒr man ha kontroll fšr att anvŠndaren inte ska kunna skapa ett trŠd
	 dŠr noderna har mindre Šn 2 barn. AlltsŒ mŒste man ha minst 2 pŒ d.
	 Enda skillnaden frŒn en binŠr heap hŠr Šr att vi tilldelar d ett vŠrde, 
	 samt att vi tar emot d som argument i konstruktorn..
	 
	 * WAS::::
	  Nothing
	 
	 */
	public DHeap(int d )
	{
		if(d<2)
			throw new IllegalArgumentException();
		this.d = d;
		this.currentSize = 0;
		this.array = (AnyType[]) new Comparable[ DEFAULT_CAPACITY + 1 ];
	}


	/*
	 €ven i denna konstruktor fŒr man Šndra sŒ att man ska ange antal barn (d) 
	 som anger hur mŒnga barn en fšrŠlder nod ska ha. DŠrfšr har vi lagt in
	 int d i konstruktorn.
	 Vi gšr inga andra Šndringar Šn det och att tilldela variabeln d ett vŠrde 
	 som anvŠndaren skickar in via konstruktorn. 
	 
	 * WAS::::   
	 public BinaryHeap( AnyType [ ] items )
     {
     currentSize = items.length;
     array = (AnyType[]) new Comparable[ ( currentSize + 2 ) * 11 / 10 ];
     
     int i = 1;
     for( AnyType item : items )
     array[ i++ ] = item;
     buildHeap( );
     }
     */
	public DHeap( AnyType [ ] items ,int d )
	{
		if(items.length<=0 || d<2)
			throw new IllegalArgumentException();

		this.d=d;
		currentSize = items.length;								//example send in 10 you get 10+2 * 11 / 10 = 20 (%2)
		array = (AnyType[]) new Comparable[ ( currentSize + 2 ) * 11 / 10 ];

		int i = 1;
		for( AnyType item : items )
			array[ i++ ] = item;  //Copy of array items to _array
		buildHeap( ); //
	}


	/* 
	 HŠr bšrjar det roliga, hŠr fŒr man Šndra utrŠkningen i for satsen frŒn 
	 hole / 2 som tidigare hŠmtat fšrŠldernodens index dŠr hole Šr storleken 
	 pŒ vŒr array + 1, och nŠr man delar hole med 2 fŒr man fšrŠldernodens index. 
	 Sedan jŠmfšr man om den nya noden x Šr stšrre eller om den Šr mindre Šn sin fšrŠldernod. 
	 €r den mindre byter man plats pŒ elementet och dess fšrŠlder. 
	 I de fall dŠr x Šr stšrre Šn sin fšrŠlder nod sŒ gšrs inte byte av plats.
	 
	 Eftersom vi har d antal barn att ta hŠnsyn till har vi skapat metoder fšr
	 att hŠmta fšrŠldernoden, metoden parentIndex tar emot hole och gšr fšljande 
	 utrŠkning: (hole - 2)/d+1 
	 I de fall dŠr d Šr satt att vara binŠrt ser vi nedan att resultatet fšr fšrŠlder
	 indexen blir samma. HŠr stoppar vi in noden pŒ nŠsta lediga plats, samt utškar
	 arrayens storlek med + 1 fšr varje gŒng. DŒ vi tidigare har tvŒ noder delade
	 vi endas hole med 2 eller multiplicera fšr att hŠmta fšrŠlder respektiva fšrstaBarnNod.
	 IstŠllet ersŠtter vi pŒ alla platser dŠr man gjorde detta med metoden 
	 parentIndex. Nedan fšljer nŒgra utŠkningar fšr att kontrollera om utrŠkningen
	 Šr eqvivalent vid en binŠrimplementering samt om det fungerar fšr en 4 heap.
	 
	 I en binŠr heap med 5 element ser vi att summan blir samma. Nedan ser vi
	 exempel pŒ en array med 5 element dŠr vi vill fŒr reda pŒ fšrŠldernodens index.
	 
	 Med hŠnsyn till d: 		(5 - 2) / 2 + 1 = 2
	 En vanlig binŠr heap : 	5/2 = 2
	 
	 Anta dŒ att vi har en 4 heap med 5 element
	 
	 (5 - 2) / 4 + 1 = 1 
	 
	 vi fŒr 1 som index alltsŒ ligger fšrŠldern till denna barnnod pŒ index 1
	 och om vi provar med en av "syskonen" som dŒ borde hamna pŒ index 2-5 
	 
	 (3 - 2) / 4 + 1 = 1
	 
	 Vi fŒr samma resultat fšrŠldern ligger pŒ index 1 fšr samtliga 4 barn.
	 
	 Fšr att klargšra tar vi ett nytt exempel anta att vi har 10 element och ska stoppa
	 in en nod x som Šr lika med 20 fšr att inte komplicera exemplet tar vi en
	 nod som Šr stšrre Šn sina fšrŠlder sŒ vi slipper sortering.
	 
	 (10 - 2) / 4 + 1 = 5
	 vi fŒr index 5
	 alltsŒ kommer noden x ha sin fšrŠlder pŒ index 5 alltsŒ elementet som hŒller
	 vŠrdet 8.
	 
	  0 1 2 3 4 5 6 7 8 9 10		=indexen
	 | |2|4|5|7|8|9|11|14|20| | 	=arrayen
	 			f		   x		= fšrŠlder samt barnnode
	 
	 * WAS::::
	
		public void insert( AnyType x )
     {
     if( currentSize == array.length - 1 )
     enlargeArray( array.length * 2 + 1 );

     // Percolate up
     int hole = ++currentSize;
     for( array[ 0 ] = x; x.compareTo( array[ hole / 2 ] ) < 0; hole /= 2 )
     array[ hole ] = array[ hole / 2 ];
     array[ hole ] = x;
     }
     */
	public void insert( AnyType x )
	{
	
		if(array.length-1==currentSize)
			enlargeArray(array.length * 2+1);

		int hole = ++currentSize;
		for(array[0] = x; hole> 1 && x.compareTo(array[parentIndex(hole)]) < 0; hole = parentIndex(hole))
			array[hole] = array[parentIndex(hole)];
		array[hole] = x;
	}


/*WAS::::
 * No change
 * */
	private void enlargeArray( int newSize )
	{
		AnyType [] old = array;
		this.array = (AnyType []) new Comparable[ newSize ];
		for( int i = 0; i < old.length; i++ )
			array[ i ] = old[ i ];        
	}


	/*WAS::::
	 * No change
	 * */
	public AnyType findMin( )
	{
		if( isEmpty())
			throw new UnderflowException("Exception" );
		return array[ 1 ];
	}


	/*WAS::::
	 * No change
	 * */
	public AnyType deleteMin( )
	{
		if(this.isEmpty())
			throw new UnderflowException("Exception" );

		AnyType minItem = findMin( );
		array[ 1 ] = array[ currentSize-- ];
		percolateDown( 1 );

		return minItem;
	}


	/*WAS::::
	 * No change
	 * */
	private void buildHeap( )
	{
		for( int i = currentSize / 2; i > 0; i-- )
			percolateDown( i );
	}


	/*WAS::::
	 * No change
	 * */
	public boolean isEmpty( )
	{
		return currentSize == 0;
	}

	/**
	 * Make the priority queue logically empty.
	 */
	/*WAS::::
	 * No change
	 * */
	public void makeEmpty( )
	{
		currentSize = 0;
	}



	/*
	 €ven hŠr fŒr man skapa en formel fšr att hŠmta fšrstaBarnNod med hŠnsyn till variabel d, som vi nŠmnde ovan 
	 rŠckte det tidigare med att ha en multiplikation med 2 dŒ vi hade en binŠr implementering.
	 HŠr Šndrar vi alla stŠllen dŠr det stŒr hole * 2 till metoden firstChildIndex som hŠmtar den
	 fšrsta barnets index av en fšrŠldernod. firstChildIndex metoden tar alltsŒ emot hole och rŠknar fšljande
	 (hole - 1) * d+2; 
	 
	 Om vi jŠmfšr med tidigare exempel
	 
	 (5 - 1) * 2 + 2 = 10
	 5*2 = 10
	 
	 och i en 4 heap
	 
	 (5-1) * 4 + 2 = 10
	 AlltsŒ ligger i en fyra heap pŒ index fem en fšrŠlder samt pŒ index 10 det fšrsta barnet samt de
	 tre fšljande platserna Šr plats fšr den f«s b noder.
	  0 1 2 3 4 5 6 7 8 9 10  11  12  13
	 | |x|x|x|x|f|x|x|x|x| b | b | b | b |
	 
	 PercolateDown anropas nŠr man tar bort rooten, i det hŠr fallet den nod med hšgst
	 prioritet. Eftersom det uppstŒr en lucka som mŒste fyllas i rooten mŒste vi gšra utrŠkningar
	 fšr omsortering. Samma sak som insert dŠr vi kolla om barnnoden Šr stšrre eller mindre Šn sina 
	 fšrŠlder. IsŒfall Šndrar vi sŒ att fšrŠldern alltid har ett mindre vŠrde Šn sina barn.
	 
	 Eftersom vi har d barn att ta hŠnsyn till har vi Šven en nŠstlad for sats som kollar samtliga syskon noder
	 och sorterar dessa i sin nivŒ.
	 
	 Rent praktiskt skapar vi d platser fšr barn fšr varje fšrŠlder nod, samt sorterar dessa.
	 
	 

	 * WAS::::
	 private void percolateDown( int hole )
     {
     int child;
     AnyType tmp = array[ hole ];
	 
     for( ; hole * 2 <= currentSize; hole = child )
     {
     child = hole * 2;
     
     if( child != currentSize &&
     array[ child + 1 ].compareTo( array[ child ] ) < 0 )
     child++;
     
     if( array[ child ].compareTo( tmp ) < 0 )
     array[ hole ] = array[ child ];
     else
     break;
     }
     array[ hole ] = tmp;
     }
	 * */
	private void percolateDown( int hole )
	{
		int child;
		AnyType tmp = array[ hole ];

		for( ; firstChildIndex(hole) <= currentSize; hole = child )
		{
			child = firstChildIndex(hole);
			int tmpchild = firstChildIndex(hole);

			for(int i = 0; i < d && tmpchild != currentSize; i++, tmpchild++){
				if(array[tmpchild+1].compareTo(array[child]) < 0)
					child = tmpchild +1;
			}

			if( array[child].compareTo(tmp) < 0)
				array[hole] = array[child];
			else
				break;
		}
		array[ hole ] = tmp;
	}
	
	/*WAS::::
	 nothing
	 Metoder fšr att testet ska gŒ igenom hŠmtar bara size och
	 hŠmtar via index hŠr gšrs inga kontroller dŒ metoderna Šr enbart 
	 fšr testFallet
	 */
	
	public int size(){ return currentSize; }

	public AnyType get(int index){ return array[index]; }

	/*WAS::::
	 nothing
	 
	 Metod fšr rŠkna ut fšrŠlderIndex fšr varje nod argumentet tar emot en int och rŠknar ut vart fšrŠlder
	 noden Šr, skickar man in root index alltsŒ 1 fŒr man exception.
	 
	 */
	public int parentIndex(int hole){
		if(hole<=1)
			throw new IllegalArgumentException();
		
		int parentIndex = (hole - 2)/d+1;
		return parentIndex;
	}

	/*WAS::::
	 nothing
	 
	 Metoder fšr rŠkna ut firstChildIndex fšr varje nod, argumentet tar Šven hŠr emot en int men rŠknar
	 ut vart den fšrstaBarnNoden Šr. 
	 */
	public int firstChildIndex(int hole){
		if(hole<=0)
			throw new IllegalArgumentException();

		int firstChildIndex = (hole - 1) * d+2;
		return firstChildIndex;

	}
	/*
	 TentafrŒga
	 I en heap uppnŒr vi en konstant tids komplexitet vid findMin. Men datastrukturen Šr inte
	 anpassad fšr att hitta tex max vŠrde i en stigande sortering. 
	 Fšr att hitta maxvŠrdet i en (min)heap fŒr vi t.ex. scanna igenom alla noder. 
	 
	 Fšrklara vid vilka tillfŠllen tidskomplexiteten uppnŒr logaritmisk 
	 komplexitet (O(log N)) vid insertion och deleteMin.
	 
	 
	 
	 
	 KŠllor
	 Weiss, Mark Allen. Data structures and algorithm analysis in Java / Mark Allen Weiss. Ð 3rd ed.
	 http://portfolio.happytrooper.com/?portfolio=d-heap
	 http://en.algoritmy.net/article/41909/D-ary-heap
*/

}
