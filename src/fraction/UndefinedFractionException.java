package fraction;

class UndefinedFractionException extends IllegalArgumentException{
 public UndefinedFractionException() {}
 public UndefinedFractionException(String msg) {
   super(msg);
 }
}