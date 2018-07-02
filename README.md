Middleware Bank Project Implementation. Project for Distributed System Classess AGH 2018.

Consist of three parts:
- Currency Server written in JS
- Bank Server written in JS
- Bank Client written in Java

Communication via GRPC node.js (Currency Server/Bank Server) and Apache Thrift
(Bank Server/Bank Client). IDL interfaces available at protos and thrift folders.


Description:

Aplikacja obsługi kont bankowych

Opis funkcjonalności
Celem zadania jest stworzenie aplikacji do obsługi kont bankowych o następującej funkcjonalności:
- obsługa kont typu Standard i Premium,
- nowe konto jest tworzone na podstawie podstawowych danych (imię, nazwisko, PESEL (stanowiący identyfikator klienta), deklarowany próg miesięcznych wpływów) - na bazie tej ostatniej informacji bank decyduje, czy konto będzie typu Standard czy Premium.
- dostęp do konta bankowego następuje w wyniku każdorazowego podania poprawnego klucza (GUID?), który jest zwracany klientowi w momencie tworzenia konta (nie ma fazy logowania się do konta),
- użytkownik konta Premium może się starać o uzyskanie kredytu  w różnych walutach. Bank  przedstawia całkowite koszty udzielenia pożyczki w żądanej walucie dla zadanego okresu (wyrażone w tej walucie oraz walucie rodzimej). Koszty powinny być skorelowane z aktualnym rynkowym kursem walut - o czym informuje Bank osobna usługa.
- użytkownik każdego typu konta może uzyskać informacje o jego aktualnym stanie - i tyle - na potrzeby zadania ta funkcjonalność jest wystarczająca
W aplikacji można więc wyróżnić trzy elementy: 1. usługę informującą banki o aktualnym kursie walut, 2. bank, 3. klienta banku.

Realizacja
Usługa informująca o aktualnym kurcie walut natychmiast po podłączeniu się jej klienta (czyli banku) przesyła kursy wszystkich obsługiwanych przez niego walut, a później okresowo (symulując zmiany ich kursu) i niezależnie dla różnych walut. Różne banki mogą być zainteresowane różnymi walutami - usługa powinna to brać pod uwagę.  Komunikację pomiędzy bankiem a usługą należy zrealizować z wykorzystaniem gRPC i mechanizmu strumieniowania (stream), a nie pollingu. Kurs walut powinien się nieco wahać by móc zaobserwować działanie usługi w czasie demonstracji zadania. Zbiór obsługiwanych walut jest zamknięty.

Komunikację między klientem banku a bankiem należy zrealizować z wykorzystaniem Ice albo Thrift. Realizując komunikację w ICE należy zaimplementować konta jako osobne obiekty tworzone przez odpowiednie factory (choć w przypadku tego zadania wielość obiektów nie znajduje uzasadnienia z inżynierskiego punktu widzenia) i rejestrowane w tablicy ASM z nazwą będącą wartością PESEL klienta i kategorią "standard" albo "premium". W przypadku realizacji zadania z wykorzystaniem Thrift powinny istnieć trzy osobne usługi - zarządzająca (tworzenie kont), obsługująca wszystkie konta typu Standard i obsługująca wszystkie konta Premium.

Aplikacja kliencka powinna mieć postać tekstową i może być minimalistyczna, lecz musi pozwalać na przetestowanie funkcjonalności aplikacji na różny sposób (musi więc być przynajmniej w części interaktywna). W szczególności powinno być możliwe łatwe przełączanie się pomiędzy kontami użytkownika (bez konieczności restartu aplikacji klienckiej). Format tekstu jest odpowiedni dla wprowadzania danych (np. liczb) z konsoli, ale interfejs IDL nie może ograniczać się do wykorzystania argumentów/pól typu string.

Interfejs IDL powinien być prosty ale zaprojektowany w sposób dojrzały (odpowiednie typy proste, właściwe wykorzystanie typów złożonych), uwzględniając możliwość wystąpienia różnego rodzaju błędów. Tam gdzie to możliwe należy wykorzystać dziedziczenie interfejsów IDL. Format tekstu jest odpowiedni dla wprowadzania danych (np. liczb) z konsoli, ale interfejs IDL nie może ograniczać się do wykorzystania argumentów/pól typu string.

Stan usługi bankowej nie musi być persystowany (nie musi przetrwać restartu).

Do realizacji zadania należy wykorzystać przynajmniej dwa różne języki programowania.

Działanie aplikacji może (nie musi) być demonstrowana na jednej maszynie. Wymagane jest uruchomienie co najmniej dwóch instancji banku. Kod źródłowy zadania powinien być demonstrowany w IDE. Aktywność poszczególnych elementów aplikacji należy odpowiednio logować (wystarczy na konsolę) by móc sprawnie ocenić poprawność jej działania.

Pliki generowane (stub, skeleton) powinny się znajdować w osobnym katalogu niż kod źródłowy klienta i serwera. Pliki stanowiące wynik kompilacji (.class, .o itp) powinny być w osobnych katalogach niż pliki źródłowe.
