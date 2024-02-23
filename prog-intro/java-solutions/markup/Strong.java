package markup;
import java.util.List;

public class Strong extends Marking {
    public Strong(List<MarkupElement> list) {
        super(list, "__", "<strong>", "</strong>");
    }
}
