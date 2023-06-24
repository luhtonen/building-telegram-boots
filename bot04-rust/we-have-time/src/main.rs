use chrono::{DateTime, Local, Utc};

fn main() {
    let utc: DateTime<Utc> = Utc::now();
    println!("UTC:   {}", utc);
    let local = Local::now();
    println!("Local: {}", local);
}
