package com.example.firstcomposable

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.firstcomposable.ui.theme.FirstComposableTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            /**
             * To improve the appearance of the composable using material design styling
             */
            FirstComposableTheme {
                Conversation(SampleData.conversationSample)
            }
        }
    }
}

/**
 * Define the structure of data class Id
 */
data class Id(val name: String, val course: String, val regNo: Int)

/**
 * Define the structure of data class Message
 */
data class Message(val author: String, val body: String)

/**
 * Design the structure of a card
 */
@Composable
fun MyCard(identity: Id){
    // The Image should be padded to follow material design guidelines
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.nosignal),
            contentDescription = "Student profile picture",
            modifier = Modifier
                // Set the image size
                .size(55.dp)
                // Clip the image to be shaped as a circle
                .clip(CircleShape)
                // Set the properties of the border
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )

        // Add a horizontal space between the image and the column
        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = identity.name,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )

            // Add a vertical spacer between the name and the course texts
            Spacer(modifier = Modifier.height(2.dp))

            Surface(shape = MaterialTheme.shapes.medium, elevation = 8.dp){
                Column{
                    Text(
                        text = identity.course,
                        modifier = Modifier.padding(all = 4.dp),
                        style = MaterialTheme.typography.body2
                    )

                    // Add a vertical spacer between the course and the regNo text
                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = identity.regNo.toString(),
                        modifier = Modifier.padding(all = 4.dp),
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
}

/**
 * Design the structure of the message card
 */
@Composable
fun MessageCard(msg: Message) {
    // Add padding around our message
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.crop2),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                // Set the image size to 40dp
                .size(40.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )

        // Add a horizontal space between the image and the column
        Spacer(modifier = Modifier.width(8.dp))

        // Variable to keep track if the messages are expanded or not
        var isExpanded by remember { mutableStateOf(false)}

        // surface color will be updated gradually from one color to the other
        val surfaceColor: Color by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary
            else MaterialTheme.colors.surface,
        )

        Column(modifier = Modifier.clickable { isExpanded =! isExpanded}) {
            Text(
                text = msg.author,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )

            // Add a vertical space between the author and message texts
            Spacer(modifier = Modifier.height(4.dp))

            Surface(

                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,

                // surface color will be changing gradually from primary to surface
                color = surfaceColor,

                // animatedContentSize will change the surface size gradually
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)



            ){
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    // If the message is expanded, we display all its
                    // content otherwise we only display the first line
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2
                )
            }

        }
    }

}


/**
 * Preview the composable in both light mode and dark mode
 */
@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewCard(){
    /**
     * To improve the appearance of the composable using material design styling
     */
    FirstComposableTheme {
        MyCard(
            Id("Bamidele David Ajewole", "Computer Science", 2101788))
    }
}

@Composable
fun Conversation(messages: List<Message>){
    LazyColumn {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}






