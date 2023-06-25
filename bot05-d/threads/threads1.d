import std.concurrency : spawn, thisTid;
import std.stdio : writeln;

void worker()
{
    writeln("Thread ", thisTid);
}

// to run execute following command:
// dmd -run threads/threads1.d
void main()
{
    int TOTAL_WORKERS = 5;
    for (int i = 0; i < TOTAL_WORKERS; ++i) {
        spawn(&worker);
    }
}
