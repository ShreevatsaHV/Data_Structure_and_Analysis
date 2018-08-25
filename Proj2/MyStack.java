import java.util.ArrayList;
import java.util.EmptyStackException;

public class MyStack<AnyType> {
	public ArrayList<AnyType> arlist=new ArrayList<>();
	
	public void push(AnyType x)
	{
		arlist.add(0,x);
	}
	public AnyType pop()
	{
		if(!isEmpty())
			return arlist.remove(0);
		else
			throw new EmptyStackException();
	}
	public boolean isEmpty() 
	{
		  return (arlist.size() == 0);
	}
	 public int size() 
	 {
		  return arlist.size();
	 }
	 public AnyType get( int idx )
	 {
		  return arlist.get(idx);
	 }
	 
	 public boolean checkSymbolBalance(ArrayList<String> list){
			MyStack<String> stack=new MyStack<>();
			for (int i = 0; i < list.size(); i++) 
			{
				if ((list.get(i).equals("{"))||(list.get(i).equals("["))||(list.get(i).equals("("))) {
					stack.push(String.valueOf(list.get(i)));
			}
				else
				{
					if(stack.size()!=0)
					{
						String str=stack.pop();
						if(str.equals("("))
						{
							if(!(list.get(i).equals(")")))
							{
								return false;
							}

						}
						else if(str.equals("["))
						{
							if(!(list.get(i).equals("]")))
							{
								return false;
							}
						}
						else if(str.equals("{"))
						{
							if(!(list.get(i).equals("}")))
							{
								return false;
							}
						}
					}
				}
			}
			return true;
		}
}


class TestStack
{
	public static void main( String [ ] args )
	{
		MyStack<String> myStack= new MyStack<>();
		String str="[{({})}]";
		ArrayList<String> list= new ArrayList<>();
		for (int i = 0; i < str.length(); i++) 
		{
		    list.add(String.valueOf(str.charAt(i)));
		}
		System.out.println("The given Symbols are "+str);
		if(myStack.checkSymbolBalance(list))
			System.out.println("The nested symbols are balanced");
		else
			System.out.println("The nested symbols are not balanced");

		MyStack<String> myStack1= new MyStack<>();
		String str1="[{({)}]";
		ArrayList<String> list1= new ArrayList<>();
		for (int i = 0; i < str1.length(); i++) 
		{
		    list1.add(String.valueOf(str1.charAt(i)));
		}
		System.out.println("The given Symbols are "+str1);
		if(myStack1.checkSymbolBalance(list1))
			System.out.println("The nested symbols are balanced");
		else
			System.out.println("The nested symbols are not balanced");
	}
}
